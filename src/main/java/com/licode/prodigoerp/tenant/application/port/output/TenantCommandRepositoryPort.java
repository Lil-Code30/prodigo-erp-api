package com.licode.prodigoerp.tenant.application.port.output;

import com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant.TenantJpaEntity;

public interface TenantCommandRepositoryPort {

    TenantJpaEntity createNewTenant(TenantJpaEntity tenantJpaEntity);
//    Tenant updateTenant(Tenant tenant); TO BE TALK
}
