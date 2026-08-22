package com.licode.prodigoerp.auth.application.service;

import com.licode.prodigoerp.auth.application.port.input.RegisterUserUseCase;
import com.licode.prodigoerp.auth.application.port.output.LoadUserPort;
import com.licode.prodigoerp.auth.application.port.output.PasswordEncoderPort;
import com.licode.prodigoerp.auth.application.port.output.RefreshTokenStorePort;
import com.licode.prodigoerp.auth.application.port.output.SaveUserPort;
import com.licode.prodigoerp.auth.application.port.input.command.AuthResponseCommand;
import com.licode.prodigoerp.auth.application.port.input.command.RegisterUserCommand;
import com.licode.prodigoerp.auth.domain.model.RefreshToken;
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

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase {

    private final LoadUserPort loadUserPort;
    private final TenantLookUpUseCase tenantLookUpUseCase;
    private final CreateTenantUseCase createTenantUseCase;
    private final TenantEntitlementUseCase  tenantEntitlementUseCase;
    private final TenantModuleSubCreateUseCase tenantModuleSubCreateUseCase;
    private final SaveUserPort saveUserPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final RefreshTokenStorePort refreshTokenStorePort;


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
        User createdUser = new User();
        Instant now = Instant.now();

        createdUser.setId(null);
        createdUser.setUsername(registerUserCommand.username());
        createdUser.setEmail(registerUserCommand.email());

        String hashPassword = passwordEncoderPort.encode(registerUserCommand.password());
        createdUser.setPassword(hashPassword);
        createdUser.setTenant(createdTenant);
        createdUser.setFirstName(registerUserCommand.firstName());
        createdUser.setLastName(registerUserCommand.lastName());
        createdUser.setStatus("ACTIVE");
        createdUser.setIsSuperAdmin(false);

        createdUser.setLastLogin(now);
        createdUser.setCreatedAt(now);
        createdUser.setUpdatedAt(now);

        String actor = "PRODIGO_ERP_API"; // SINCE it is the system creating the user
        createdUser.setCreatedBy(actor);
        createdUser.setUpdatedBy(actor);

        User fetchedUser = saveUserPort.save(createdUser);

        RefreshToken refreshToken = refreshTokenStorePort.createRefreshToken(fetchedUser);




        return null;
    }
}
