package com.licode.prodigoerp.auth.application.port.input.command;

public record AuthResponseCommand(
        Long userId,
        String tenantSlug,
        String accessToken,
        String refreshToken
) {
}
