package com.licode.prodigoerp.auth.domain.command;

public record AuthResponseCommand(
        Long userId,
        String tenantSlug,
        String accessToken,
        String refreshToken
) {
}
