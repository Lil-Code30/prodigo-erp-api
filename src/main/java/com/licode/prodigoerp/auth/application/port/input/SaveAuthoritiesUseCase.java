package com.licode.prodigoerp.auth.application.port.input;

import com.licode.prodigoerp.auth.application.port.input.command.AssignRoleCommand;
import com.licode.prodigoerp.auth.application.port.input.command.CreateRoleCommand;
import com.licode.prodigoerp.auth.domain.model.Role;

public interface SaveAuthoritiesUseCase {

    Role saveRole(CreateRoleCommand roleCommand);
    void assignedRoleToUser(AssignRoleCommand assignRoleCommand);
}
