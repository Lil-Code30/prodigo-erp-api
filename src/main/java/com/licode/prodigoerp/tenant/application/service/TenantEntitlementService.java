package com.licode.prodigoerp.tenant.application.service;

import com.licode.prodigoerp.tenant.application.port.input.TenantEntitlementUseCase;
import com.licode.prodigoerp.tenant.application.port.output.TenantEntitlementCreatePort;
import com.licode.prodigoerp.tenant.domain.model.Tenant;
import com.licode.prodigoerp.tenant.domain.model.TenantEntitlement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TenantEntitlementService implements TenantEntitlementUseCase {
    private final TenantEntitlementCreatePort tenantEntitlementCreatePort;

    @Override
    public TenantEntitlement createDefaultTenantEntitlement(Tenant tenant) {

        TenantEntitlement newEntitlement = new TenantEntitlement();

        newEntitlement.setTenant(tenant);
        newEntitlement.setMaxUsers(5);
        newEntitlement.setMaxStorageGb(5);
        newEntitlement.setMaxProducts(20L);

        Instant now = Instant.now();
        newEntitlement.setCreatedAt(now);
        newEntitlement.setUpdatedAt(now);
        newEntitlement.setCreatedBy(tenant.getCreatedBy());
        newEntitlement.setUpdatedBy(tenant.getUpdatedBy());

        return tenantEntitlementCreatePort.createDefaultTenantEntitlement(newEntitlement);
    }
}
