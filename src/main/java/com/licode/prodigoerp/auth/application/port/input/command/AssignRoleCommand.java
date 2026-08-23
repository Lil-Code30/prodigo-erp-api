package com.licode.prodigoerp.auth.application.port.input.command;


public record AssignRoleCommand(
        Long userId,
        Long roleId,
        Long tenantId,
        String assignBy
) {
}
