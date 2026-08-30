package com.licode.prodigoerp.tenant.adapter.output.persistence.TenantEntitlement;

import com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant.TenantJpaMapper;
import com.licode.prodigoerp.tenant.domain.model.TenantEntitlement;

public class TenantEntitlementJpaMapper {

    public static TenantEntitlementJpaEntity toDbCreateTenantEntitlement(TenantEntitlement tenantEntitlement) {
        TenantEntitlementJpaEntity tenantEntitlementJpaEntity = new TenantEntitlementJpaEntity();

        tenantEntitlementJpaEntity.setId(null);
        tenantEntitlementJpaEntity.setTenantJpaEntity(TenantJpaMapper.toJpaEntity(tenantEntitlement.getTenant()));
        tenantEntitlementJpaEntity.setMaxUsers(tenantEntitlement.getMaxUsers());
        tenantEntitlementJpaEntity.setMaxStorageGb(tenantEntitlement.getMaxStorageGb());
        tenantEntitlementJpaEntity.setMaxProducts(tenantEntitlement.getMaxProducts());
        tenantEntitlementJpaEntity.setCreatedAt(tenantEntitlement.getCreatedAt());
        tenantEntitlementJpaEntity.setUpdatedAt(tenantEntitlement.getUpdatedAt());
        tenantEntitlementJpaEntity.setCreatedBy(tenantEntitlement.getCreatedBy());
        tenantEntitlementJpaEntity.setUpdatedBy(tenantEntitlement.getUpdatedBy());

        return tenantEntitlementJpaEntity;
    }

    public static TenantEntitlementJpaEntity toJpaEntity(TenantEntitlement tenantEntitlement) {
        TenantEntitlementJpaEntity tenantEntitlementJpaEntity = new TenantEntitlementJpaEntity();

        tenantEntitlementJpaEntity.setId(tenantEntitlement.getId());
        tenantEntitlementJpaEntity.setTenantJpaEntity(TenantJpaMapper.toJpaEntity(tenantEntitlement.getTenant()));
        tenantEntitlementJpaEntity.setMaxUsers(tenantEntitlement.getMaxUsers());
        tenantEntitlementJpaEntity.setMaxStorageGb(tenantEntitlement.getMaxStorageGb());
        tenantEntitlementJpaEntity.setMaxProducts(tenantEntitlement.getMaxProducts());
        tenantEntitlementJpaEntity.setCreatedAt(tenantEntitlement.getCreatedAt());
        tenantEntitlementJpaEntity.setUpdatedAt(tenantEntitlement.getUpdatedAt());
        tenantEntitlementJpaEntity.setCreatedBy(tenantEntitlement.getCreatedBy());
        tenantEntitlementJpaEntity.setUpdatedBy(tenantEntitlement.getUpdatedBy());

        return tenantEntitlementJpaEntity;
    }

    public static TenantEntitlement toDomainModel(TenantEntitlementJpaEntity tenantEntitlementJpaEntity) {
        TenantEntitlement tenantEntitlement = new TenantEntitlement();

        tenantEntitlement.setId(tenantEntitlementJpaEntity.getId());
        tenantEntitlement.setTenant(TenantJpaMapper.toDomainModel(tenantEntitlementJpaEntity.getTenantJpaEntity()));
        tenantEntitlement.setMaxUsers(tenantEntitlementJpaEntity.getMaxUsers());
        tenantEntitlement.setMaxStorageGb(tenantEntitlementJpaEntity.getMaxStorageGb());
        tenantEntitlement.setMaxProducts(tenantEntitlementJpaEntity.getMaxProducts());
        tenantEntitlement.setCreatedAt(tenantEntitlementJpaEntity.getCreatedAt());
        tenantEntitlement.setUpdatedAt(tenantEntitlementJpaEntity.getUpdatedAt());
        tenantEntitlement.setCreatedBy(tenantEntitlementJpaEntity.getCreatedBy());
        tenantEntitlement.setUpdatedBy(tenantEntitlementJpaEntity.getUpdatedBy());

        return tenantEntitlement;
    }
}
