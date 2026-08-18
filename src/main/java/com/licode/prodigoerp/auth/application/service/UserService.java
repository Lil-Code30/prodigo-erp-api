package com.licode.prodigoerp.auth.application.service;

import com.licode.prodigoerp.auth.application.port.input.RegisterUserUseCase;
import com.licode.prodigoerp.auth.application.port.output.query.UserQueryRepositoryPort;
import com.licode.prodigoerp.auth.domain.command.AuthResponseCommand;
import com.licode.prodigoerp.auth.domain.command.RegisterUserCommand;
import com.licode.prodigoerp.auth.domain.exception.ConflictException;
import com.licode.prodigoerp.tenant.application.port.input.CreateTenantUseCase;
import com.licode.prodigoerp.tenant.application.port.input.TenantEntitlementUseCase;
import com.licode.prodigoerp.tenant.application.port.input.TenantLookUpUseCase;
import com.licode.prodigoerp.tenant.domain.command.CreateTenantCommand;
import com.licode.prodigoerp.tenant.domain.model.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase {

    private final UserQueryRepositoryPort userQueryRepositoryPort;
    private final TenantLookUpUseCase tenantLookUpUseCase;
    private final CreateTenantUseCase createTenantUseCase;
    private final TenantEntitlementUseCase  tenantEntitlementUseCase;


    @Override
    public AuthResponseCommand register(RegisterUserCommand registerUserCommand) {

        // check if the email, username already exist in the db
        if(userQueryRepositoryPort.findUserByUsername(registerUserCommand.username()).isPresent()){
            throw new ConflictException("Username already exists");
        }

        if(userQueryRepositoryPort.findUserByEmail(registerUserCommand.email()).isPresent() ) {
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


        return null;
    }
}
