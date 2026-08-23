package com.licode.prodigoerp.auth.application.service;

import com.licode.prodigoerp.auth.application.port.input.SaveAuthoritiesUseCase;
import com.licode.prodigoerp.auth.application.port.input.command.AssignRoleCommand;
import com.licode.prodigoerp.auth.application.port.input.command.CreatePermissionCommand;
import com.licode.prodigoerp.auth.application.port.input.command.CreateRoleCommand;
import com.licode.prodigoerp.auth.application.port.output.LoadUserPort;
import com.licode.prodigoerp.auth.application.port.output.RoleQueryPort;
import com.licode.prodigoerp.auth.application.port.output.SavePermissionPort;
import com.licode.prodigoerp.auth.application.port.output.SaveRolePort;
import com.licode.prodigoerp.auth.domain.model.*;
import com.licode.prodigoerp.common.exception.NotFoundException;
import com.licode.prodigoerp.module.application.port.input.ModuleLookUpUseCase;
import com.licode.prodigoerp.module.domain.model.Module;
import com.licode.prodigoerp.tenant.application.port.input.TenantLookUpUseCase;
import com.licode.prodigoerp.tenant.domain.model.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthoritiesService implements SaveAuthoritiesUseCase {

    private final SaveRolePort saveRolePort;
    private final SavePermissionPort savePermissionPort;
    private final ModuleLookUpUseCase moduleLookUpUseCase;
    private final TenantLookUpUseCase tenantLookUpUseCase;
    private final LoadUserPort loadUserPort;
    private final RoleQueryPort roleQueryPort;

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

        userRole.setId(null);

        Tenant tenant = tenantLookUpUseCase.findTenantById(assignRoleCommand.tenantId());
        Optional<User> user = loadUserPort.findUserById(assignRoleCommand.userId());

        if (user.isEmpty()) {
            throw new NotFoundException("User not found with id: " + assignRoleCommand.userId());
        }

        Optional<Role> role = roleQueryPort.findRoleByIdAndTenantId(assignRoleCommand.roleId(), tenant.getId());

        if (role.isEmpty()) {
            throw new NotFoundException("Role not found with id: " + assignRoleCommand.roleId());
        }

        Instant now = Instant.now();

        userRole.setRole(role.get());
        userRole.setUser(user.get());
        userRole.setTenantId(tenant.getId());
        userRole.setAssignedAt(now);
        userRole.setAssignedBy(assignRoleCommand.assignBy());
        userRole.setExpiresAt(now.plusSeconds(315576000)); // TODO (to be refactor) expires in 10 years

        saveRolePort.saveUserRole(userRole);
    }

    @Override
    public Permission savePermission(CreatePermissionCommand permissionCommand) {
        Permission permission = new Permission();
        Instant now = Instant.now();

        String permissionCode = permissionCommand.resource() + "." + permissionCommand.action();

        // We got the module key, then we need to fetch the whole Module object to create the associate permission
        Optional<Module> module = moduleLookUpUseCase.findModuleByModuleKey(permissionCommand.moduleKey());

        if (module.isEmpty()) {
            throw  new NotFoundException("Module with key " + permissionCommand.moduleKey() +" not found");
        }

        permission.setId(null);
        permission.setCode(permissionCode);
        permission.setDescription(permissionCommand.description());
        permission.setAction(permissionCommand.action());
        permission.setResource(permissionCommand.resource());
        permission.setModule(module.get());

        permission.setCreatedAt(now);
        permission.setUpdatedAt(now);
        permission.setCreatedBy(permissionCommand.author());
        permission.setUpdatedBy(permissionCommand.author());

        return savePermissionPort.savePermission(permission);
    }

    @Override
    public void assignedPermissionToRole(Long permissionId, AssignRoleCommand assignRoleCommand) {

        RolePermission rolePermission = new RolePermission();
        rolePermission.setId(null);

        Optional<Role> role = roleQueryPort.findRoleByIdAndTenantId(assignRoleCommand.roleId(), assignRoleCommand.tenantId());

        if (role.isEmpty()) {
            throw new NotFoundException("Role not found with id: " + assignRoleCommand.roleId());
        }

        Optional<Permission> permission = roleQueryPort.findPermissionById(permissionId);

        if (permission.isEmpty()) {
            throw new NotFoundException("Permission not found with id: " + permissionId);
        }

        rolePermission.setPermission(permission.get());
        rolePermission.setRole(role.get());
        rolePermission.setGrantedAt(Instant.now());
        rolePermission.setGrantedBy(assignRoleCommand.assignBy());
    }


}
