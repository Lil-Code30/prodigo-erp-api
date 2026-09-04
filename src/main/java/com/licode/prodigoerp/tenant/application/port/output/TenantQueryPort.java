package com.licode.prodigoerp.tenant.application.port.output;

import com.licode.prodigoerp.tenant.domain.model.Tenant;

import java.util.Optional;
import java.util.UUID;

public interface TenantQueryPort {

    Optional<Tenant> findTenantById(UUID id);
    Optional<Tenant> findTenantBySlug(String slug);
    Boolean existsBySlug(String slug);
    Boolean existsByIdAndActive(UUID id);
}
