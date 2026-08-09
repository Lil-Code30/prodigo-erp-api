package com.licode.prodigoerp.user.mapper;

import com.licode.prodigoerp.auth.dto.AuthResponse;
import com.licode.prodigoerp.auth.dto.RegisterRequest;
import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.common.security.CustomUserDetailsService;
import com.licode.prodigoerp.common.security.JwtUtil;
import com.licode.prodigoerp.tenant.entity.Tenant;
import com.licode.prodigoerp.user.dto.RegisterAdminRequest;
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


    public User toUserEntity(RegisterRequest registerRequest, Tenant tenant, Instant now) {
        User user = new User();


        user.setEmail(registerRequest.email());
        user.setUsername(registerRequest.username());
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setStatus("ACTIVE");
        user.setIsSuperAdmin(false);
        user.setLastLogin(now);

        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setCreatedBy(SystemConstants.SYSTEM_NAME);
        user.setUpdatedBy(SystemConstants.SYSTEM_NAME);

        user.setTenant(tenant);

        return user;
    }

    public User toAdminEntity(RegisterAdminRequest registerAdminRequest, Instant now) {
        User user = new User();


        user.setEmail(registerAdminRequest.email());
        user.setUsername(registerAdminRequest.username());
        user.setFirstName(registerAdminRequest.firstName());
        user.setLastName(registerAdminRequest.lastName());
        user.setPassword(passwordEncoder.encode(registerAdminRequest.password()));
        user.setStatus("ACTIVE");
        user.setIsSuperAdmin(true);
        user.setLastLogin(now);

        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setCreatedBy(SystemConstants.SYSTEM_NAME);
        user.setUpdatedBy(SystemConstants.SYSTEM_NAME);

        user.setTenant(null);

        return user;
    }

    public AuthResponse toAuthResponse(User user, String refreshToken ) {
        UserPrincipal userPrincipal = customUserDetailsService.buildPrincipal(user);

        String accessToken = jwtUtil.generateAccessToken(userPrincipal);
//        String refreshToken = refreshTokenService.issueFor(user).getToken();

        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();

        if(user.getTenant() != null) {
            return new AuthResponse(
                    user.getId(), user.getTenant().getSlug(), accessToken,  refreshToken, authorities
            );
        }else {
            return new AuthResponse(user.getId(), null, accessToken,  refreshToken, authorities);
        }


    }
}
