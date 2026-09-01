package com.licode.prodigoerp.auth.adapter.input.rest.dto;


import java.util.List;
import java.util.UUID;

public record AuthResponseDto(
        UUID userId,
        String tenantSlug,
        String accessToken,
        List<String> roles,
        List<String> permissions
) {
}
