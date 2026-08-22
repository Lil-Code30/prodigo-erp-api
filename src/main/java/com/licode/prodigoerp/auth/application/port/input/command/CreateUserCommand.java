package com.licode.prodigoerp.auth.application.port.input.command;

import com.licode.prodigoerp.tenant.domain.model.Tenant;

public record CreateUserCommand(
         String username,
         Tenant tenant,
         String email,
         String password,
         String firstName,
         String lastName,
         Boolean isSuperAdmin
) {
}
