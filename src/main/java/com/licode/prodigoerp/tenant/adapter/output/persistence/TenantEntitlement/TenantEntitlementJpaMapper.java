package com.licode.prodigoerp.tenant.adapter.output.persistence.TenantEntitlement;

import com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant.TenantJpaEntity;
import com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant.TenantJpaMapper;
import com.licode.prodigoerp.tenant.domain.model.TenantEntitlement;

public class TenantEntitlementJpaMapper {

    public static TenantJpaEntitlement toDbCreateTenantEntitlement(TenantEntitlement tenantEntitlement) {
        TenantJpaEntitlement tenantJpaEntitlement = new TenantJpaEntitlement();

        tenantJpaEntitlement.setId(null);
        tenantJpaEntitlement.setTenantJpaEntity(TenantJpaMapper.toJpaEntity(tenantEntitlement.getTenant()));
        tenantJpaEntitlement.setMaxUsers(tenantEntitlement.getMaxUsers());
        tenantJpaEntitlement.setMaxStorageGb(tenantEntitlement.getMaxStorageGb());
        tenantJpaEntitlement.setMaxProducts(tenantEntitlement.getMaxProducts());
        tenantJpaEntitlement.setCreatedAt(tenantEntitlement.getCreatedAt());
        tenantJpaEntitlement.setUpdatedAt(tenantEntitlement.getUpdatedAt());
        tenantJpaEntitlement.setCreatedBy(tenantEntitlement.getCreatedBy());
        tenantJpaEntitlement.setUpdatedBy(tenantEntitlement.getUpdatedBy());

        return tenantJpaEntitlement;
    }

    public static TenantJpaEntitlement toJpaEntity(TenantEntitlement tenantEntitlement) {
        TenantJpaEntitlement tenantJpaEntitlement = new TenantJpaEntitlement();

        tenantJpaEntitlement.setId(tenantEntitlement.getId());
        tenantJpaEntitlement.setTenantJpaEntity(TenantJpaMapper.toJpaEntity(tenantEntitlement.getTenant()));
        tenantJpaEntitlement.setMaxUsers(tenantEntitlement.getMaxUsers());
        tenantJpaEntitlement.setMaxStorageGb(tenantEntitlement.getMaxStorageGb());
        tenantJpaEntitlement.setMaxProducts(tenantEntitlement.getMaxProducts());
        tenantJpaEntitlement.setCreatedAt(tenantEntitlement.getCreatedAt());
        tenantJpaEntitlement.setUpdatedAt(tenantEntitlement.getUpdatedAt());
        tenantJpaEntitlement.setCreatedBy(tenantEntitlement.getCreatedBy());
        tenantJpaEntitlement.setUpdatedBy(tenantEntitlement.getUpdatedBy());

        return tenantJpaEntitlement;
    }

    public static TenantEntitlement toDomainModel(TenantJpaEntitlement tenantJpaEntitlement) {
        TenantEntitlement tenantEntitlement = new TenantEntitlement();

        tenantEntitlement.setId(tenantJpaEntitlement.getId());
        tenantEntitlement.setTenant(TenantJpaMapper.toDomainModel(tenantJpaEntitlement.getTenantJpaEntity()));
        tenantEntitlement.setMaxUsers(tenantJpaEntitlement.getMaxUsers());
        tenantEntitlement.setMaxStorageGb(tenantJpaEntitlement.getMaxStorageGb());
        tenantEntitlement.setMaxProducts(tenantJpaEntitlement.getMaxProducts());
        tenantEntitlement.setCreatedAt(tenantJpaEntitlement.getCreatedAt());
        tenantEntitlement.setUpdatedAt(tenantJpaEntitlement.getUpdatedAt());
        tenantEntitlement.setCreatedBy(tenantJpaEntitlement.getCreatedBy());
        tenantEntitlement.setUpdatedBy(tenantJpaEntitlement.getUpdatedBy());

        return tenantEntitlement;
    }
}
