package com.licode.prodigoerp.auth.adapter.input.rest.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public record AuthResponseDto(
        UUID userId,
        String tenantSlug,
        String accessToken
//        Collection<? extends GrantedAuthority> authorities
        // TODO Find a way to pass this to the controller both (register and login)
) {
}
