package com.licode.prodigoerp.auth.application.service;

import com.licode.prodigoerp.auth.application.port.input.SaveAuthoritiesUseCase;
import com.licode.prodigoerp.auth.application.port.input.command.AssignRoleCommand;
import com.licode.prodigoerp.auth.application.port.input.command.CreateRoleCommand;
import com.licode.prodigoerp.auth.application.port.output.SaveRolePort;
import com.licode.prodigoerp.auth.domain.model.Role;
import com.licode.prodigoerp.auth.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthoritiesService implements SaveAuthoritiesUseCase {

    private final SaveRolePort saveRolePort;

    @Override
    public Role saveRole(CreateRoleCommand roleCommand) {

        Role role = new Role();

        Instant now = Instant.now();

        role.setId(null);
        role.setName(roleCommand.roleName());
        role.setDescription(roleCommand.description());
        role.setTenant(roleCommand.tenant());
        role.setIsDefault(roleCommand.isDefault());

        role.setCreatedAt(now);
        role.setUpdatedAt(now);
        role.setCreatedBy(roleCommand.author());
        role.setUpdatedBy(roleCommand.author());

        return saveRolePort.saveRole(role);
    }

    @Override
    public void assignedRoleToUser(AssignRoleCommand assignRoleCommand) {

        UserRole userRole = new UserRole();

        saveRolePort.saveUserRole(userRole);
    }
}
