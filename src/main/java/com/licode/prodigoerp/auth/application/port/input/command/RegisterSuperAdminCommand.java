package com.licode.prodigoerp.auth.application.port.input.command;

public record RegisterSuperAdminCommand(
        String username,
        String email,
        String password,
        String firstName,
        String lastName
) {
}
