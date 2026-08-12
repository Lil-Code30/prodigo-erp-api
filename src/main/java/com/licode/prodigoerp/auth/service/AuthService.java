package com.licode.prodigoerp.auth.service;

import com.licode.prodigoerp.auth.dto.AuthResponse;
import com.licode.prodigoerp.auth.dto.LoginRequest;
import com.licode.prodigoerp.auth.dto.RefreshResponse;
import com.licode.prodigoerp.auth.dto.RegisterRequest;
import com.licode.prodigoerp.auth.entity.RefreshToken;
import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.common.exception.ConflictException;
import com.licode.prodigoerp.common.exception.NotFoundException;
import com.licode.prodigoerp.common.security.CustomUserDetailsService;
import com.licode.prodigoerp.common.security.JwtUtil;
import com.licode.prodigoerp.module.dto.RegisterSelectedModule;
import com.licode.prodigoerp.module.entity.Module;
import com.licode.prodigoerp.module.repository.ModuleRepository;
import com.licode.prodigoerp.module.service.ModuleService;
import com.licode.prodigoerp.tenant.entity.Tenant;
import com.licode.prodigoerp.tenant.entity.TenantEntitlement;
import com.licode.prodigoerp.tenant.mapper.TenantMapper;
import com.licode.prodigoerp.tenant.repository.TenantRepository;
import com.licode.prodigoerp.tenant.service.TenantEntitlementService;
import com.licode.prodigoerp.user.entity.Role;
import com.licode.prodigoerp.user.entity.User;
import com.licode.prodigoerp.user.entity.UserPrincipal;
import com.licode.prodigoerp.user.mapper.UserMapper;
import com.licode.prodigoerp.user.repository.UserRepository;
import com.licode.prodigoerp.user.service.RoleService;
import com.licode.prodigoerp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;
    private final TenantRepository tenantRepository;
    private final TenantEntitlementService tenantEntitlementService;
    private final RoleService roleService;
    private final ModuleService moduleService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {

        // check if the email, username already exist in the db
        if( userRepository.findByUsername(registerRequest.username()).isPresent() ) {
            throw new ConflictException("Username already exists");
        }

        if(userRepository.findByEmail(registerRequest.email()).isPresent() ) {
            throw new ConflictException("Email already exists");
        };

        // also need to check is the Company exist or not in the db
        if(tenantRepository.existsBySlug(registerRequest.companySlug())){
            throw new ConflictException("Company Name already exists");
        }

        Instant  now = Instant.now();

        // creating a tenant while registering the user
        // IMPORTANT: here the principle is that user creating his account is the owner of the company
        // there will be endpoints for the users registration on a specific tenant
        Tenant newTenant = TenantMapper.toEntity(
                registerRequest.companyName(),
                registerRequest.companySlug(),
                registerRequest.country(),
                registerRequest.username(),
                now
        );

        Tenant fetchedTenant =  tenantRepository.save(newTenant);

        // After creating the tenant, we need to create its tenant entitlements
        // with the system default values found in "package com.licode.prodigoerp.common - SystemConstants"
        tenantEntitlementService.createDefault(newTenant);

        // Then we need to create the moduleSub from the selected module provided
        // NOTE: the first module in the list of the selected module is free
        // TODO: handle the module subscription

        moduleService.createTenantModuleSubscription(fetchedTenant.getId(), registerRequest.selectedModules());

        // creating user infos
        User newUser = userMapper.toUserEntity(
                registerRequest,
                fetchedTenant,
                now
        );


        // NOTE: if I don't save the user first before generating a refreshToken, there will be an error
        userRepository.save(newUser);
        RefreshToken refreshToken = refreshTokenService.issueFor(newUser);

//        newUser.getRefreshToken().add(refreshToken);
        User fetchedUser = userRepository.save(newUser);


        // we need to now create the ADMIN ROLE
        Role adminRole = roleService.createAdminRole(fetchedTenant);

        // Then we need to associate the admin role to the user
        roleService.assignedRoleToUser(
                fetchedUser,
                adminRole,
                fetchedTenant.getId(),
                SystemConstants.SYSTEM_NAME
        );


        return userMapper.toAuthResponse(fetchedUser, refreshToken.getToken());
    }


    public AuthResponse login(LoginRequest loginRequest) {
       Authentication authenticatedUser =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

       if( !authenticatedUser.isAuthenticated()){
            throw new BadCredentialsException("Bad credentials");
       }


        Optional<User> user = userService.findByUsername(loginRequest.username());

       if(user.isEmpty()){
           throw new NotFoundException("User not found");
       }

       // TODO : Need to figure out how to manage the refresh token system
        // don't want a new refresh token for every login
       RefreshToken refreshToken = refreshTokenService.issueFor(user.get());


        return userMapper.toAuthResponse(user.get(),  refreshToken.getToken());
    }

    ///NOTE: for the refresh token flow
    /// The refresh token string (from the request)
    /// With this string, we can find the RefreshToken object that will also contain the user infos
    /// If a wrong refresh token string is send by the request, we will not be able to find the user
    /// leading to no generation of the new access token
    @Transactional
    public RefreshResponse refreshAccessToken(String token) {

      Optional<RefreshToken> refreshToken  = refreshTokenService.getRefreshTokenByTokenString(token);

      if( refreshToken.isEmpty()){
          throw new NotFoundException("Refresh token not found");
      }

      RefreshToken refreshToken1 = refreshToken.get();


      // need to do verification to be sure the Refresh token is a valid Token
      if(refreshToken1.getIsRevoked()){
          throw new AccessDeniedException("Access Denied! Refresh token is revoked");
      }

      if(refreshToken1.getExpiryDate().isBefore(Instant.now())){
          throw new BadCredentialsException("Bad credentials! Your token has expired");
      }

      User user = refreshToken1.getUser();

        UserPrincipal userPrincipal = customUserDetailsService.buildPrincipal(user);

        String accessToken = jwtUtil.generateAccessToken(userPrincipal);

        // NOTE: before issuing any new refresh token, revoke the old ones
        refreshTokenService.revoke(refreshToken1.getToken());

        RefreshToken newRefreshToken = refreshTokenService.issueFor(user);

        return new RefreshResponse(accessToken, newRefreshToken.getToken());

    }

    // This is meant to convert each Modules in the format RegisterSelectedModule(ModuleId, ModuleName, moduleKey)
    public List<RegisterSelectedModule> getAllSelectedModule(){
        List<Module> allModules = moduleRepository.findAll();

        // convert Module Entity to SelectedModule
        return allModules.stream().map(
                m -> {
                    return  new RegisterSelectedModule(
                            m.getId(),
                            m.getName(),
                            m.getModuleKey()
                    );
                }
        ).toList();
    }
}
