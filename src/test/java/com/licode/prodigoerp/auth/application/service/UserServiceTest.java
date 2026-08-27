package com.licode.prodigoerp.auth.application.service;

import com.licode.prodigoerp.auth.application.port.input.SaveAuthoritiesUseCase;
import com.licode.prodigoerp.auth.application.port.input.SaveUserUseCase;
import com.licode.prodigoerp.auth.application.port.input.command.*;
import com.licode.prodigoerp.auth.application.port.output.LoadUserPort;
import com.licode.prodigoerp.auth.application.port.output.RefreshTokenStorePort;
import com.licode.prodigoerp.auth.application.port.output.TokenGeneratorPort;
import com.licode.prodigoerp.auth.domain.model.Permission;
import com.licode.prodigoerp.auth.domain.model.RefreshToken;
import com.licode.prodigoerp.auth.domain.model.Role;
import com.licode.prodigoerp.auth.domain.model.User;
import com.licode.prodigoerp.common.exception.ConflictException;
import com.licode.prodigoerp.module.application.port.input.TenantModuleSubCreateUseCase;
import com.licode.prodigoerp.module.application.port.input.command.SelectedModuleCommand;
import com.licode.prodigoerp.module.domain.model.Module;
import com.licode.prodigoerp.tenant.application.port.input.CreateTenantUseCase;
import com.licode.prodigoerp.tenant.application.port.input.TenantEntitlementUseCase;
import com.licode.prodigoerp.tenant.application.port.input.TenantLookUpUseCase;
import com.licode.prodigoerp.tenant.application.port.input.command.CreateTenantCommand;
import com.licode.prodigoerp.tenant.domain.model.Tenant;
import com.licode.prodigoerp.tenant.domain.model.TenantEntitlement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private  LoadUserPort loadUserPort;
    @Mock private  TenantLookUpUseCase tenantLookUpUseCase;
    @Mock private  CreateTenantUseCase createTenantUseCase;
    @Mock private  TenantEntitlementUseCase tenantEntitlementUseCase;
    @Mock private  TenantModuleSubCreateUseCase tenantModuleSubCreateUseCase;
    @Mock private  RefreshTokenStorePort refreshTokenStorePort;
    @Mock private  SaveUserUseCase saveUserUseCase;
    @Mock private  SaveAuthoritiesUseCase saveAuthoritiesUseCase;
    @Mock private  TokenGeneratorPort tokenGeneratorPort;

    private UserService userService;  // what we want to test

    private static final String ACTOR = "PRODIGO_EPR_API";

    private RegisterUserCommand registerUserCommand;
    private Tenant tenant;
    private TenantEntitlement tenantEntitlement;
    private User user;
    private Role adminRole;
    private RefreshToken refreshToken;
    private Map<String, Module> subscriptions;

    @BeforeEach
    void setUp(){
        userService = new UserService(
                loadUserPort,
                tenantLookUpUseCase,
                createTenantUseCase,
                tenantEntitlementUseCase,
                tenantModuleSubCreateUseCase,
                refreshTokenStorePort,
                saveUserUseCase,
                saveAuthoritiesUseCase,
                tokenGeneratorPort
        );

        registerUserCommand = new RegisterUserCommand(
                "Cameroon Tech Solutions",
                "cameroon-tech-solutions",
                "CM",
                "ismael.loko",
                "ismael.loko@example.com",
                "Test@123456",
                "Ismael",
                "Loko",
                List.of(
                        new SelectedModuleCommand(1L, "CRM", "CRM"),
                        new SelectedModuleCommand(2L, "Inventory", "INVENTORY")
                )
        );

        tenant = new Tenant();
        tenant.setId(1L);
        tenant.setName(registerUserCommand.companyName());
        tenant.setSlug(registerUserCommand.companySlug());
        tenant.setCountry(registerUserCommand.country());

        tenantEntitlement = new TenantEntitlement();
        tenantEntitlement.setId(1L);
        tenantEntitlement.setTenant(tenant);

        user = new User();
        user.setId(10L);
        user.setUsername(registerUserCommand.username());
        user.setEmail(registerUserCommand.email());
        user.setTenant(tenant);

        adminRole = new Role();
        adminRole.setId(100L);
        adminRole.setName("ADMIN");
        adminRole.setTenant(tenant);

        refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token-value");
        refreshToken.setUser(user);

        Module crm = new Module();
        crm.setId(1L);
        crm.setName("CRM");
        crm.setModuleKey("CRM");

        Module inventory = new Module();
        inventory.setId(2L);
        inventory.setName("Inventory");
        inventory.setModuleKey("INVENTORY");

        subscriptions = new LinkedHashMap<>();
        subscriptions.put("CRM", crm);
        subscriptions.put("Inventory", inventory);
    }

    @Test
    @DisplayName("Happy path: create tenant, user, admin role, permissions and return tokens")
    void shouldRegisterUSerSuccessfully() {
        // Given
        when(loadUserPort.findUserByUsername(registerUserCommand.username()))
                .thenReturn(Optional.empty());
        when(loadUserPort.findUserByEmail(registerUserCommand.email()))
                .thenReturn(Optional.empty());
        when(tenantLookUpUseCase.existsBySlug(registerUserCommand.companySlug()))
                .thenReturn(false);
        when(createTenantUseCase.create(any(CreateTenantCommand.class)))
                .thenReturn(tenant);
        when(tenantEntitlementUseCase.createDefaultTenantEntitlement(tenant))
                .thenReturn(tenantEntitlement);
        when(tenantModuleSubCreateUseCase.createTenantModuleSubscription(eq(tenant.getId()),eq(registerUserCommand.selectedModules())))
                .thenReturn(subscriptions);
        when(saveAuthoritiesUseCase.savePermission(any(CreatePermissionCommand.class), eq(ACTOR)))
                .thenReturn(new Permission());
        when(refreshTokenStorePort.createRefreshToken(user)).thenReturn(refreshToken);
        when(tokenGeneratorPort.generateAccessToken(user)).thenReturn("access-token-value");

        // When

        AuthResponseCommand response = userService.register(registerUserCommand);

        // then : the response reflects what the mocked collaborators returned
        verify(createTenantUseCase).create(any(CreateTenantCommand.class));
        verify(tenantEntitlementUseCase).createDefaultTenantEntitlement(tenant);
        verify(saveUserUseCase).save(any(CreateUserCommand.class), eq(ACTOR));
        verify(saveAuthoritiesUseCase).saveRole(any(CreateRoleCommand.class));
        verify(saveAuthoritiesUseCase).assignedRoleToUser(any(AssignRoleCommand.class));

        verify(saveAuthoritiesUseCase, times(subscriptions.size()))
                .savePermission(any(CreatePermissionCommand.class), eq(ACTOR));
        verify(saveAuthoritiesUseCase, times(subscriptions.size()))
                .assignedPermissionToRole(any(), any(AssignRoleCommand.class));
        verify(refreshTokenStorePort).createRefreshToken(user);
        verify(tokenGeneratorPort).generateAccessToken(user);
    }

}