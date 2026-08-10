package com.licode.prodigoerp.tenant.service;

import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.tenant.entity.Tenant;
import com.licode.prodigoerp.tenant.entity.TenantEntitlement;
import com.licode.prodigoerp.tenant.repository.TenantEntitlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TenantEntitlementService {
    private final TenantEntitlementRepository tenantEntitlementRepository;


    public void createDefault(Tenant tenant) {

        TenantEntitlement tenantEntitlement = new TenantEntitlement();

        tenantEntitlement.setTenant(tenant);
        tenantEntitlement.setMaxUsers(SystemConstants.DEFAULT_MAX_USERS);
        tenantEntitlement.setMaxStorageGb(SystemConstants.DEFAULT_MAX_STORAGE_GB);
        tenantEntitlement.setMaxProducts(SystemConstants.DEFAULT_MAX_PRODUCTS);

        Instant now = Instant.now();
        tenantEntitlement.setCreatedAt(now);
        tenantEntitlement.setCreatedBy(SystemConstants.SYSTEM_NAME);
        tenantEntitlement.setUpdatedAt(now);
        tenantEntitlement.setUpdatedBy(SystemConstants.SYSTEM_NAME);

        tenantEntitlementRepository.save(tenantEntitlement);
    }
}
