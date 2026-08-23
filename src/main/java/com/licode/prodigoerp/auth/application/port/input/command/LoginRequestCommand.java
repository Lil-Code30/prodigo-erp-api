package com.licode.prodigoerp.auth.application.port.input.command;

public record LoginRequestCommand(
        String username,
        String password
) {
}
