package com.licode.prodigoerp.tenant.application.port.output;

import com.licode.prodigoerp.tenant.domain.model.Tenant;

import java.util.Optional;

public interface TenantQueryRepositoryPort {

    Optional<Tenant> findTenantById(Long id);
    Optional<Tenant> findTenantBySlug(String slug);
    Boolean existsBySlug(String slug);
}
