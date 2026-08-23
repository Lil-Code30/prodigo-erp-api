package com.licode.prodigoerp.auth.application.port.input.command;

import java.util.List;

public record AuthResponseCommand(
        Long userId,
        String tenantSlug,
        String accessToken,
        String refreshToken
) {
}
