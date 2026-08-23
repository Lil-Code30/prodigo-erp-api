package com.licode.prodigoerp.auth.adapter.input.rest.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record AuthResponseDto(
        Long userId,
        String tenantSlug,
        String accessToken,
        String refreshToken
//        Collection<? extends GrantedAuthority> authorities
        // TODO need to pass this to the controller
) {
}
