package com.licode.prodigoerp.common_old.security.dto;

public record JwtPrincipal(
        Long userId,
        String username,
        String email,
        Long tenantId,
        String tenantSlug
) {}
