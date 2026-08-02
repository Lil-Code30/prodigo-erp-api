package com.licode.prodigoerp.auth.service;

import com.licode.prodigoerp.auth.dto.AuthResponse;
import com.licode.prodigoerp.auth.dto.RegisterRequest;
import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.tenant.entity.Tenant;
import com.licode.prodigoerp.tenant.mapper.TenantMapper;
import com.licode.prodigoerp.tenant.repository.TenantRepository;
import com.licode.prodigoerp.user.entity.Role;
import com.licode.prodigoerp.user.entity.User;
import com.licode.prodigoerp.user.entity.UserRole;
import com.licode.prodigoerp.user.mapper.UserMapper;
import com.licode.prodigoerp.user.repository.UserRepository;
import com.licode.prodigoerp.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {

        // check if the email, username already exist in the db
//        if( userRepository.findByUsername(registerRequest.username()).isPresent() ) {
//            throw new ConflictException("Username already exists");
//        }
//
//        if(userRepository.findByEmail(registerRequest.email()).isPresent() ) {
//            throw new ConflictException("Email already exists");
//        };
//
//        // also need to check is the Company exist or not in the db
//        if(!tenantRepository.existsBySlug(registerRequest.companySlug())){
//            throw new ConflictException("Company Name already exists");
//        }

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

        // creating user infos

        User newUser = userMapper.toEntity(
                registerRequest,
                fetchedTenant,
                now,
                false);

        User fetchedUser = userRepository.save(newUser);

        // we need to now create the ADMIN ROLE
        Role adminRole = roleService.createAdminRole(fetchedTenant);

        // Then we need to associate the admin role to the user
        UserRole userRole = roleService.assignedRoleToUser(
                fetchedUser,
                adminRole,
                fetchedTenant.getId(),
                SystemConstants.SYSTEM_NAME
        );


        return userMapper.toAuthResponse(fetchedUser);
    }
}
