package com.licode.prodigoerp.auth.application.port.input.command;



public record CreatePermissionCommand(
        String description,
        String action,
        String resource
) {
}
