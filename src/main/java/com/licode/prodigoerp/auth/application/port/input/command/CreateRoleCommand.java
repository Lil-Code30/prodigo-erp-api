package com.licode.prodigoerp.auth.application.port.input.command;

import com.licode.prodigoerp.tenant.domain.model.Tenant;

public record CreateRoleCommand(
        String roleName,
        Tenant tenant,
        String description,
        boolean isDefault,
        String author
) {
}
