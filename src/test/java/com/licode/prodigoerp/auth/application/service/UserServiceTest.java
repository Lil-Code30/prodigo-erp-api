package com.licode.prodigoerp.auth.application.service;

import com.licode.prodigoerp.auth.application.port.input.SaveAuthoritiesUseCase;
import com.licode.prodigoerp.auth.application.port.input.SaveUserUseCase;
import com.licode.prodigoerp.auth.application.port.input.command.*;
import com.licode.prodigoerp.auth.application.port.output.LoadUserPort;
import com.licode.prodigoerp.auth.application.port.output.RefreshTokenStorePort;
import com.licode.prodigoerp.auth.application.port.output.TokenGeneratorPort;
import com.licode.prodigoerp.auth.domain.model.Permission;
import com.licode.prodigoerp.auth.domain.model.Role;
import com.licode.prodigoerp.auth.domain.model.User;
import com.licode.prodigoerp.common.exception.ConflictException;
import com.licode.prodigoerp.module.application.port.input.TenantModuleSubCreateUseCase;
import com.licode.prodigoerp.module.application.port.input.command.SelectedModuleCommand;
import com.licode.prodigoerp.tenant.application.port.input.CreateTenantUseCase;
import com.licode.prodigoerp.tenant.application.port.input.TenantEntitlementUseCase;
import com.licode.prodigoerp.tenant.application.port.input.TenantLookUpUseCase;
import com.licode.prodigoerp.tenant.application.port.input.command.CreateTenantCommand;
import com.licode.prodigoerp.tenant.domain.model.Tenant;
import com.licode.prodigoerp.tenant.domain.model.TenantEntitlement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private  LoadUserPort loadUserPort;
    @Mock
    private  TenantLookUpUseCase tenantLookUpUseCase;
    @Mock
    private  CreateTenantUseCase createTenantUseCase;
    @Mock
    private  TenantEntitlementUseCase tenantEntitlementUseCase;
    @Mock
    private  TenantModuleSubCreateUseCase tenantModuleSubCreateUseCase;
    @Mock
    private  RefreshTokenStorePort refreshTokenStorePort;
    @Mock
    private  SaveUserUseCase saveUserUseCase;
    @Mock
    private  SaveAuthoritiesUseCase saveAuthoritiesUseCase;
    @Mock
    private  TokenGeneratorPort tokenGeneratorPort;

    @InjectMocks
    private UserService userService;  // what we want to test

    private RegisterUserCommand registerUserCommand;
//    private SelectedModuleCommand selectedModuleCommand;
    private CreateTenantCommand createTenantCommand;
    private Tenant testTenant;
    private TenantEntitlement testTenantEntitlement;
    private CreateUserCommand createUserCommand;
    private User testUser;
    private CreateRoleCommand createRoleCommand;
    private Role testRole;
    private CreatePermissionCommand createPermissionCommand;
    private Permission testPermission;
    private AssignRoleCommand assignRoleCommand;
    private String actor;
//    private AuthResponseCommand authResponseCommand;

    @BeforeEach
    void setUp() {

        this.actor = "PRODIGO_ERP_API";


        this.registerUserCommand = new RegisterUserCommand(
                "Cameroon Tech Solutions",
                "cameroon-tech-solutions",
                "CM",
                "ismael.loko",
                "ismael.loko@example.com",
                "Test@123456",
                "Ismael",
                "Loko",
                List.of(
                        new SelectedModuleCommand(
                                1L,
                                "CRM",
                                "CRM"
                        ),
                        new SelectedModuleCommand(
                                2L,
                                "Inventory",
                                "INVENTORY"
                        )
                )
        );

        this.testTenant = new Tenant();

        testTenant.setId(1L);
        testTenant.setName("Cameroon Tech Solutions");
        testTenant.setSlug("cameroon-tech-solutions");
        testTenant.setCountry("CM");
        testTenant.setStatus("ACTIVE");
        testTenant.setCreatedAt(Instant.parse("2026-08-26T12:00:00Z"));
        testTenant.setUpdatedAt(Instant.parse("2026-08-26T12:00:00Z"));
        testTenant.setCreatedBy(actor);
        testTenant.setUpdatedBy(actor);

         testUser = new User();

        testUser.setId(1L);
        testUser.setUsername(this.registerUserCommand.username());
        testUser.setTenant(this.testTenant);
        testUser.setEmail(this.registerUserCommand.email());
        testUser.setPassword("$2a$10$testEncodedPassword");
        testUser.setFirstName(this.registerUserCommand.firstName());
        testUser.setLastName(this.registerUserCommand.lastName());
        testUser.setStatus("ACTIVE");
        testUser.setIsSuperAdmin(false);
        testUser.setLastLogin(Instant.parse("2026-08-26T18:30:00Z"));
        testUser.setCreatedAt(Instant.parse("2026-08-20T10:00:00Z"));
        testUser.setUpdatedAt(Instant.parse("2026-08-26T18:30:00Z"));
        testUser.setCreatedBy(actor);
        testUser.setUpdatedBy(actor);

        this.createTenantCommand = new CreateTenantCommand(
                this.registerUserCommand.companyName(),
                this.registerUserCommand.companySlug(),
                this.registerUserCommand.country()
        );

        testTenantEntitlement = new TenantEntitlement();

        testTenantEntitlement.setId(1L);
        testTenantEntitlement.setTenant(testTenant);
        testTenantEntitlement.setMaxUsers(10);
        testTenantEntitlement.setMaxStorageGb(25);
        testTenantEntitlement.setMaxProducts(100L);
        testTenantEntitlement.setCreatedAt(Instant.parse("2026-08-20T10:00:00Z"));
        testTenantEntitlement.setUpdatedAt(Instant.parse("2026-08-26T18:00:00Z"));
        testTenantEntitlement.setCreatedBy("system");
        testTenantEntitlement.setUpdatedBy("system");
    }


    @Test
    void shouldRegisterUserSuccessfully() {

        // Given
        when(createTenantUseCase.create(createTenantCommand))
                .thenReturn(testTenant);
        when(tenantEntitlementUseCase.createDefaultTenantEntitlement(any(Tenant.class)))
                .thenReturn(testTenantEntitlement);
        when(saveUserUseCase.save(createUserCommand, actor))
                .thenReturn(testUser);

        // when
        AuthResponseCommand authResponseCommand = userService.register(registerUserCommand);

        // then
        assertNotNull(authResponseCommand);
        assertEquals(authResponseCommand.tenantSlug(), registerUserCommand.companySlug());
        verify(loadUserPort, times(1)).findUserByUsername(registerUserCommand.username());
    }

//    @Test
//    void shouldThrowExceptionWhenUsernameExists() {
//        String testUsername = "ismael.loko";
//
//        // given
//        when(loadUserPort.findUserByUsername(this.registerUserCommand.username())).thenReturn();
//    }
}