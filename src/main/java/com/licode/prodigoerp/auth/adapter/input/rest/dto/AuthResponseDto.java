package com.licode.prodigoerp.auth.adapter.input.rest.dto;

public record AuthResponseDto(
        Long userId,
        String tenantSlug,
        String accessToken,
        String refreshToken
        // TODO need a way to add granted Authorities
) {
}
