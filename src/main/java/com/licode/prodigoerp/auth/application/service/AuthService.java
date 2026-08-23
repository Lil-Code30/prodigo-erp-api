package com.licode.prodigoerp.auth.application.service;

import com.licode.prodigoerp.auth.adapter.input.rest.dto.RefreshResponseDto;
import com.licode.prodigoerp.auth.adapter.output.persistence.refreshToken.RefreshTokenJpaEntity;
import com.licode.prodigoerp.auth.application.port.input.command.AuthResponseCommand;
import com.licode.prodigoerp.auth.application.port.input.command.LoginRequestCommand;
import com.licode.prodigoerp.auth.application.port.output.LoadUserPort;
import com.licode.prodigoerp.auth.application.port.output.RefreshTokenStorePort;
import com.licode.prodigoerp.auth.application.port.output.TokenGeneratorPort;
import com.licode.prodigoerp.auth.domain.model.RefreshToken;
import com.licode.prodigoerp.auth.domain.model.User;
import com.licode.prodigoerp.common.exception.NotFoundException;
import com.licode.prodigoerp.common.security.JwtUtil;
import com.licode.prodigoerp.auth.adapter.input.security.UserPrincipal;
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
public class AuthService{

    private final AuthenticationManager authenticationManager;
    private final LoadUserPort loadUserPort;
    private final RefreshTokenStorePort refreshTokenStorePort;
    private final TokenGeneratorPort tokenGeneratorPort;
    private final JwtUtil jwtUtil;


//    public AuthResponseCommand login(LoginRequestCommand loginRequest) {
//
//        // TODO to be move to the controller
//        Authentication authenticatedUser =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginRequest.username(), loginRequest.password()
//        ));
//
//
//       if( !authenticatedUser.isAuthenticated()){
//            throw new BadCredentialsException("Bad credentials");
//       }
//
//        Optional<User> user = loadUserPort.findUserByUsername(loginRequest.username());
//
//       if(user.isEmpty()){
//           throw new NotFoundException("User not found");
//       }
//
//        // TODO : Need to figure out how to manage the refresh token system
//        // don't want a new refresh token for every login
//
//        RefreshToken refreshToken = refreshTokenStorePort.createRefreshToken(user.get());
//       String accessToken = tokenGeneratorPort.generateAccessToken(user.get());
//
//
//
//       String identify = user.get().getIsSuperAdmin() ? "SUPERADMIN" : "USER";
//
//    }

    ///NOTE: for the refresh token flow
    /// The refresh token string (from the request)
    /// With this string, we can find the RefreshToken object that will also contain the user infos
    /// If a wrong refresh token string is send by the request, we will not be able to find the user
    /// leading to no generation of the new access token
//    @Transactional
//    public RefreshResponseDto refreshAccessToken(String token) {
//
//      Optional<RefreshTokenJpaEntity> refreshToken  = refreshTokenService.getRefreshTokenByTokenString(token);
//
//      if( refreshToken.isEmpty()){
//          throw new NotFoundException("Refresh token not found");
//      }
//
//      RefreshTokenJpaEntity refreshTokenJpaEntity1 = refreshToken.get();
//
//
//      // need to do verification to be sure the Refresh token is a valid Token
//      if(refreshTokenJpaEntity1.getIsRevoked()){
//          throw new AccessDeniedException("Access Denied! Refresh token is revoked");
//      }
//
//      if(refreshTokenJpaEntity1.getExpiryDate().isBefore(Instant.now())){
//          throw new BadCredentialsException("Bad credentials! Your token has expired");
//      }
//
//      User user = refreshTokenJpaEntity1.getUser();
//
//        UserPrincipal userPrincipal = customUserDetailsService.buildPrincipal(user);
//
//        String accessToken = jwtUtil.generateAccessToken(userPrincipal);
//
//        // NOTE: before issuing any new refresh token, revoke the old ones
//        refreshTokenService.revoke(refreshTokenJpaEntity1.getToken());
//
//        RefreshTokenJpaEntity newRefreshTokenJpaEntity = refreshTokenService.issueFor(user);
//
//        return new RefreshResponseDto(accessToken, newRefreshTokenJpaEntity.getToken());
//
//    }
//
//    // This is meant to convert each Modules in the format RegisterSelectedModule(ModuleId, ModuleName, moduleKey)
//    public List<RegisterSelectedModuleDto> getAllSelectedModule(){
//        List<ModuleJpaEntity> allModuleJpaEntities = moduleRepository.findAll();
//
//        // convert Module Entity to SelectedModule
//        return allModuleJpaEntities.stream().map(
//                m -> {
//                    return  new RegisterSelectedModuleDto(
//                            m.getId(),
//                            m.getName(),
//                            m.getModuleKey()
//                    );
//                }
//        ).toList();
//    }
}
