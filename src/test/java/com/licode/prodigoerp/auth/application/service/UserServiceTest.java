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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    private static final String ACTOR = "PRODIGO_ERP_API";

    // Fixed UUIDs (not random) so assertions stay deterministic and easy to read/debug
    private static final UUID TENANT_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID TENANT_ENTITLEMENT_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    private static final UUID USER_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    private static final UUID ADMIN_ROLE_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
    private static final UUID CRM_MODULE_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");
    private static final UUID INVENTORY_MODULE_ID = UUID.fromString("66666666-6666-6666-6666-666666666666");

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
                        new SelectedModuleCommand(CRM_MODULE_ID, "CRM", "CRM"),
                        new SelectedModuleCommand(INVENTORY_MODULE_ID, "Inventory", "INVENTORY")
                )
        );

        tenant = new Tenant();
        tenant.setId(TENANT_ID);
        tenant.setName(registerUserCommand.companyName());
        tenant.setSlug(registerUserCommand.companySlug());
        tenant.setCountry(registerUserCommand.country());

        tenantEntitlement = new TenantEntitlement();
        tenantEntitlement.setId(TENANT_ENTITLEMENT_ID);
        tenantEntitlement.setTenant(tenant);

        user = new User();
        user.setId(USER_ID);
        user.setUsername(registerUserCommand.username());
        user.setEmail(registerUserCommand.email());
        user.setTenant(tenant);

        adminRole = new Role();
        adminRole.setId(ADMIN_ROLE_ID);
        adminRole.setName("ADMIN");
        adminRole.setTenant(tenant);

        refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token-value");
        refreshToken.setUser(user);

        Module crm = new Module();
        crm.setId(CRM_MODULE_ID);
        crm.setName("CRM");
        crm.setModuleKey("CRM");

        Module inventory = new Module();
        inventory.setId(INVENTORY_MODULE_ID);
        inventory.setName("Inventory");
        inventory.setModuleKey("INVENTORY");

        subscriptions = new LinkedHashMap<>();
        subscriptions.put("CRM", crm);
        subscriptions.put("Inventory", inventory);
    }

    @Nested
    @DisplayName("register user test")
    class RegisterUser{
        @Test
        @DisplayName("Happy path: create tenant, user, admin role, permissions and return tokens")
        void shouldRegisterUserSuccessfully() {
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
            when(saveUserUseCase.save(any(CreateUserCommand.class), eq(ACTOR)))
                    .thenReturn(user);
            when(saveAuthoritiesUseCase.savePermission(any(CreatePermissionCommand.class), eq(ACTOR)))
                    .thenReturn(new Permission());
            when(saveAuthoritiesUseCase.saveRole(any(CreateRoleCommand.class)))
                    .thenReturn(adminRole);
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

            verify(createTenantUseCase).create(argThat(tenant -> tenant.companySlug().equals(registerUserCommand.companySlug())));
            verify(saveUserUseCase).save(argThat(user -> user.tenant().getId().equals(TENANT_ID)),  eq(ACTOR));
        }

        @Test
        @DisplayName("Rejects registration when the username already exists")
        void shouldRejectRegistrationWhenUsernameAlreadyExists() {
            when(loadUserPort.findUserByUsername(registerUserCommand.username()))
                    .thenReturn(Optional.of(user));

            ConflictException ex = assertThrows(ConflictException.class,
                    () -> userService.register(registerUserCommand));

            assertEquals("Username already exists", ex.getMessage());
//            verify(createTenantUseCase, never()).create(any());
            verifyNoMoreInteractions(createTenantUseCase);
//            verify(saveUserUseCase, never()).save(any(), any());
            verifyNoInteractions(saveUserUseCase);
        }

        @Test
        @DisplayName("Rejects registration when the email already exists")
        void shouldThrowConflictWhenEmailAlreadyExists() {
            when(loadUserPort.findUserByUsername(registerUserCommand.username()))
                    .thenReturn(Optional.empty());
            when(loadUserPort.findUserByEmail(registerUserCommand.email()))
                    .thenReturn(Optional.of(user));

            ConflictException ex = assertThrows(ConflictException.class,
                    () -> userService.register(registerUserCommand));

            assertEquals("Email already exists", ex.getMessage());
//            verify(createTenantUseCase, never()).create(any());
            verifyNoMoreInteractions(createTenantUseCase);
        }

        @Test
        @DisplayName("Rejects registration when the company slug already exists")
        void shouldThrowConflictWhenCompanySlugAlreadyExists() {
            when(loadUserPort.findUserByUsername(registerUserCommand.username()))
                    .thenReturn(Optional.empty());
            when(loadUserPort.findUserByEmail(registerUserCommand.email()))
                    .thenReturn(Optional.empty());
            when(tenantLookUpUseCase.existsBySlug(registerUserCommand.companySlug()))
                    .thenReturn(true);

            ConflictException ex = assertThrows(ConflictException.class,
                    () -> userService.register(registerUserCommand));

            assertEquals("Company Name already exists", ex.getMessage());
//            verify(createTenantUseCase, never()).create(any());
            verifyNoMoreInteractions(createTenantUseCase);
        }
    }
}