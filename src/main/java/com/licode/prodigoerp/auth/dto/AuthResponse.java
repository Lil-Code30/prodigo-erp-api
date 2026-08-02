package com.licode.prodigoerp.auth.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record AuthResponse(
        Long userId,
        String tenantSlug,
        String accessToken,
        String refreshToken,
        Collection<? extends GrantedAuthority>  authorities
) {}