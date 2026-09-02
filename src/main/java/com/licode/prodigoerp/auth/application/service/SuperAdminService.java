package com.licode.prodigoerp.auth.application.service;

import com.licode.prodigoerp.auth.application.port.input.RegisterSuperAdminUseCase;
import com.licode.prodigoerp.auth.application.port.input.SaveAuthoritiesUseCase;
import com.licode.prodigoerp.auth.application.port.input.SaveUserUseCase;
import com.licode.prodigoerp.auth.application.port.input.command.AssignRoleCommand;
import com.licode.prodigoerp.auth.application.port.input.command.CreateRoleCommand;
import com.licode.prodigoerp.auth.application.port.input.command.CreateUserCommand;
import com.licode.prodigoerp.auth.application.port.input.command.RegisterSuperAdminCommand;
import com.licode.prodigoerp.auth.application.port.output.LoadUserPort;
import com.licode.prodigoerp.auth.application.port.output.RoleQueryPort;
import com.licode.prodigoerp.auth.domain.model.Permission;
import com.licode.prodigoerp.auth.domain.model.Role;
import com.licode.prodigoerp.auth.domain.model.User;
import com.licode.prodigoerp.common.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuperAdminService implements RegisterSuperAdminUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserUseCase saveUserUseCase;
    private final RoleQueryPort roleQueryPort;
    private final SaveAuthoritiesUseCase saveAuthoritiesUseCase;

    @Override
    @Transactional
    public String register(RegisterSuperAdminCommand registerSuperAdminCommand) {

        // check if the email, username already exist in the db
        if(loadUserPort.findUserByUsername(registerSuperAdminCommand.username()).isPresent()){
            throw new ConflictException("Username already exists");
        }

        if(loadUserPort.findUserByEmail(registerSuperAdminCommand.email()).isPresent() ) {
            throw new ConflictException("Email already exists");
        };

        // TODO: need to get the username of the person connected
        String author = "PRODIGO_ERP_API";

        User fetchedUser = saveUserUseCase.save(
                new CreateUserCommand(
                        registerSuperAdminCommand.username(),
                        null,
                        registerSuperAdminCommand.email(),
                        registerSuperAdminCommand.password(),
                        registerSuperAdminCommand.firstName(),
                        registerSuperAdminCommand.lastName(),
                        true
                ),
                author
        );

        // We need to create/assigne the default super admin role and permissions
        String defaultRoleName = "SUPER_ADMIN";
        String defaultPermissionCode = "ERP.SYSTEM.READ";

        // need to fetch if the default role already exist
        Optional<Role> fetchedRole = roleQueryPort.findRoleByNameWithTenantNull(defaultRoleName);

        // get the role if already exist
        // orElse create  the default role
        Role defaultRole = fetchedRole.orElseGet(() -> saveAuthoritiesUseCase.saveRole(
                new CreateRoleCommand(
                        defaultRoleName,
                        null,
                        "SUPER_ADMIN : The Default role to access the ERP System Dashboard",
                        true,
                        author
                )
        ));

        saveAuthoritiesUseCase.assignedRoleToUser(
                new AssignRoleCommand(
                        fetchedUser.getId(),
                        defaultRole.getId(),
                        null,
                        author
                )
        );

        // Then we fetched/create the default permission
        Optional<Permission> fetchedPermission = roleQueryPort.findPermissionByCode(defaultPermissionCode);


        return "";
    }
}
