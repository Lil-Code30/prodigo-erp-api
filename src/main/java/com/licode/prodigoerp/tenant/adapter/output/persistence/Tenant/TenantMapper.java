package com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant;

import com.licode.prodigoerp.tenant.adapter.input.rest.dto.RegisterTenant;
import com.licode.prodigoerp.tenant.domain.model.Tenant;

import java.time.Instant;

public class TenantMapper {

    public static TenantJpaEntity toDbCreateTenant(RegisterTenant registerTenant) {
        TenantJpaEntity tenantJpaEntity = new TenantJpaEntity();

        Instant instant = Instant.now();

        tenantJpaEntity.setId(null);
        tenantJpaEntity.setSlug(registerTenant.companySlug());
        tenantJpaEntity.setName(registerTenant.companyName());
        tenantJpaEntity.setCountry(registerTenant.country());
        tenantJpaEntity.setStatus("ACTIVE");
        tenantJpaEntity.setCreatedAt(instant);
        tenantJpaEntity.setUpdatedAt(instant);
        tenantJpaEntity.setCreatedBy("PRODIGO_ERP_API");
        tenantJpaEntity.setUpdatedBy("PRODIGO_ERP_API");

        return tenantJpaEntity;

    }

    public static TenantJpaEntity toJpaEntity(Tenant  tenant) {
        TenantJpaEntity tenantJpaEntity = new TenantJpaEntity();

        tenantJpaEntity.setId(tenant.getId());
        tenantJpaEntity.setSlug(tenant.getSlug());
        tenantJpaEntity.setName(tenant.getName());
        tenantJpaEntity.setCountry(tenant.getCountry());
        tenantJpaEntity.setStatus(tenant.getStatus());
        tenantJpaEntity.setCreatedAt(tenant.getCreatedAt());
        tenantJpaEntity.setUpdatedAt(tenant.getUpdatedAt());
        tenantJpaEntity.setCreatedBy(tenant.getCreatedBy());
        tenantJpaEntity.setUpdatedBy(tenant.getUpdatedBy());

        return tenantJpaEntity;
    }

    public static Tenant toJpaEntity(TenantJpaEntity tenantJpaEntity) {
        Tenant tenant = new Tenant();

        tenant.setId(tenantJpaEntity.getId());
        tenant.setSlug(tenantJpaEntity.getSlug());
        tenant.setName(tenantJpaEntity.getName());
        tenant.setCountry(tenantJpaEntity.getCountry());
        tenant.setStatus(tenantJpaEntity.getStatus());
        tenant.setCreatedAt(tenantJpaEntity.getCreatedAt());
        tenant.setUpdatedAt(tenantJpaEntity.getUpdatedAt());
        tenant.setCreatedBy(tenantJpaEntity.getCreatedBy());
        tenant.setUpdatedBy(tenantJpaEntity.getUpdatedBy());

        return tenant;
    }
}
