package com.licode.prodigoerp.user.mapper;

import com.licode.prodigoerp.auth.dto.AuthResponse;
import com.licode.prodigoerp.auth.dto.RegisterRequest;
import com.licode.prodigoerp.auth.entity.RefreshToken;
import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.common.security.CustomUserDetailsService;
import com.licode.prodigoerp.common.security.JwtUtil;
import com.licode.prodigoerp.tenant.entity.Tenant;
import com.licode.prodigoerp.user.entity.User;
import com.licode.prodigoerp.user.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;


    public User toEntity(RegisterRequest registerRequest, Tenant tenant, Instant now, Boolean isSuperAdmin) {
        User user = new User();

        user.setEmail(registerRequest.email());
        user.setUsername(registerRequest.username());
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setIsActive(true);
        user.setIsSuperAdmin(isSuperAdmin);
        user.setLastLogin(now);

        // TODO To be implemented
//        user.setRefreshToken(new RefreshToken());

        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setCreatedBy(SystemConstants.SYSTEM_NAME);
        user.setUpdatedBy(SystemConstants.SYSTEM_NAME);

        user.setTenant(tenant);

        return user;
    }

    public AuthResponse toAuthResponse(User user ) {
        UserPrincipal userPrincipal = customUserDetailsService.buildPrincipal(user);
        String accessToken = jwtUtil.generateAccessToken(userPrincipal);

        // TODO: since we haven't yet implement the logic to manage the refresh token,
        // we use the accessToken just for testing
        String refreshToken = jwtUtil.generateAccessToken(userPrincipal);

        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();

        return new AuthResponse(
            user.getId(), user.getTenant().getSlug(), accessToken,  refreshToken, authorities
        );
    }
}
