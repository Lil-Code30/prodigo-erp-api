package com.licode.prodigoerp.auth.application.service;

import com.licode.prodigoerp.auth.application.port.input.RegisterUserUseCase;
import com.licode.prodigoerp.auth.application.port.input.SaveAuthoritiesUseCase;
import com.licode.prodigoerp.auth.application.port.input.SaveUserUseCase;
import com.licode.prodigoerp.auth.application.port.input.command.*;
import com.licode.prodigoerp.auth.application.port.output.LoadUserPort;
import com.licode.prodigoerp.auth.application.port.output.RefreshTokenStorePort;
import com.licode.prodigoerp.auth.domain.model.Permission;
import com.licode.prodigoerp.auth.domain.model.RefreshToken;
import com.licode.prodigoerp.auth.domain.model.Role;
import com.licode.prodigoerp.common.exception.ConflictException;
import com.licode.prodigoerp.auth.domain.model.User;
import com.licode.prodigoerp.module.application.port.input.TenantModuleSubCreateUseCase;
import com.licode.prodigoerp.module.domain.model.Module;
import com.licode.prodigoerp.tenant.application.port.input.CreateTenantUseCase;
import com.licode.prodigoerp.tenant.application.port.input.TenantEntitlementUseCase;
import com.licode.prodigoerp.tenant.application.port.input.TenantLookUpUseCase;
import com.licode.prodigoerp.tenant.application.port.input.command.CreateTenantCommand;
import com.licode.prodigoerp.tenant.domain.model.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase {

    private final LoadUserPort loadUserPort;
    private final TenantLookUpUseCase tenantLookUpUseCase;
    private final CreateTenantUseCase createTenantUseCase;
    private final TenantEntitlementUseCase  tenantEntitlementUseCase;
    private final TenantModuleSubCreateUseCase tenantModuleSubCreateUseCase;
    private final RefreshTokenStorePort refreshTokenStorePort;
    private final SaveUserUseCase saveUserUseCase;
    private final SaveAuthoritiesUseCase saveAuthoritiesUseCase;



    @Override
    public AuthResponseCommand register(RegisterUserCommand registerUserCommand) {

        // check if the email, username already exist in the db
        if(loadUserPort.findUserByUsername(registerUserCommand.username()).isPresent()){
            throw new ConflictException("Username already exists");
        }

        if(loadUserPort.findUserByEmail(registerUserCommand.email()).isPresent() ) {
            throw new ConflictException("Email already exists");
        };

        // also need to check is the Company exist or not in the db
        if(tenantLookUpUseCase.existsBySlug(registerUserCommand.companySlug())){
            throw new ConflictException("Company Name already exists");
        }

        // creating a tenant while registering the user
        // IMPORTANT: here the principle is that user creating his account is the owner of the company
        // there will be endpoints for the users registration on a specific tenant
        Tenant createdTenant = createTenantUseCase.create(
                new CreateTenantCommand(
                        registerUserCommand.companyName(),
                        registerUserCommand.companySlug(),
                        registerUserCommand.country()
                )
        );

        // After creating the tenant, we need to create its tenant entitlements (with the default values)
        tenantEntitlementUseCase.createDefaultTenantEntitlement(createdTenant);

        // Then we need to create the moduleSub from the selected module provided
        // NOTE: the first module in the list of the selected module is free
        // handle the module subscription
        Map<String, Module> subscriptions = tenantModuleSubCreateUseCase.createTenantModuleSubscription(
                createdTenant.getId(),
                registerUserCommand.selectedModules()
        );

        // Save the user to the DB but first need to build the user
        String author = "PRODIGO_ERP_API"; // SINCE it is the system creating the user

        User fetchedUser = saveUserUseCase.save(
                new CreateUserCommand(
                        registerUserCommand.username(),
                        createdTenant,
                        registerUserCommand.email(),
                        registerUserCommand.password(),
                        registerUserCommand.firstName(),
                        registerUserCommand.lastName(),
                        false
                ),
                author
        );

        RefreshToken refreshToken = refreshTokenStorePort.createRefreshToken(fetchedUser);

        // here is the Admin role ( for the company (tenant) creating the account)
        Role adminRole = saveAuthoritiesUseCase.saveRole(
                new CreateRoleCommand(
                        "ADMIN",
                        createdTenant,
                        "ROLE_ADMIN : This is the role that has full access to the Tenant (Company)",
                        false,
                        author
                )
        );

        // Then we need to associate the admin role to the user
        saveAuthoritiesUseCase.assignedRoleToUser(
                new AssignRoleCommand(
                        fetchedUser,
                        adminRole,
                        createdTenant.getId(),
                        author
                )
        );

        // we need to create permission/module for the role created :
        //NOTE: since this will be the endpoint for the Tenant Admin creation,
        // he/she will have all the permissions i.e. module_CRUD (e.x: CRM_CRUD or INVOICE_CRUD)
        // This is taking the module key as the resource + the action which is the CRUD
        subscriptions.forEach((key, value) -> {

            CreatePermissionCommand createPermissionCommand = new CreatePermissionCommand(
                    "Complete (FULL) Access to the " + value.getName() + " Module",
                    value.getModuleKey(),
                    "CRUD",
                    value.getModuleKey(),
                    author
            );

            Permission permission = saveAuthoritiesUseCase.savePermission(createPermissionCommand);

            // For every permission created, we should assign the permission to that role
            // TODO Assign Permission to the role
        });




        return null;
    }
}
