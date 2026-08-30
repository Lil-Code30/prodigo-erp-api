package com.licode.prodigoerp.auth.application.port.input.command;


import java.util.UUID;

public record AssignRoleCommand(
        UUID userId,
        UUID roleId,
        UUID tenantId,
        String assignBy
) {
}
