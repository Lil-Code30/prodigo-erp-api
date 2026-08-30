package com.licode.prodigoerp.tenant.application.port.input;

import com.licode.prodigoerp.tenant.domain.model.Tenant;

import java.util.Optional;
import java.util.UUID;

public interface TenantLookUpUseCase {

    Boolean existsBySlug(String slug);
    Tenant findTenantById(UUID tenantId);
}
