package com.licode.prodigoerp.auth.application.port.input.command;

public record RefreshResponseCommand(
        String accessToken,
        String refreshToken
) {
}
