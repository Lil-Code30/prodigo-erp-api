package com.licode.prodigoerp.auth.application.port.input.command;

import com.licode.prodigoerp.auth.domain.model.Role;
import com.licode.prodigoerp.auth.domain.model.User;

public record AssignRoleCommand(
        User user,
        Role role,
        Long tenantId,
        String assignBy
) {
}
