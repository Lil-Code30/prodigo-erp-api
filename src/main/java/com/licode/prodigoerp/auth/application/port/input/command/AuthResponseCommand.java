package com.licode.prodigoerp.auth.application.port.input.command;

import java.util.List;
import java.util.UUID;

public record AuthResponseCommand(
        UUID userId,
        String tenantSlug,
        String accessToken,
        String refreshToken,
        List<String> roles,
        List<String> permissions
) {
}
