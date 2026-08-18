package com.licode.prodigoerp.tenant.application.port.output;

import com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant.TenantJpaEntity;

import java.util.Optional;

public interface TenantQueryRepositoryPort {

    Optional<TenantJpaEntity> findTenantById(Long id);
    Optional<TenantJpaEntity> findTenantBySlug(String slug);
    Boolean existsBySlug(String slug);
}
