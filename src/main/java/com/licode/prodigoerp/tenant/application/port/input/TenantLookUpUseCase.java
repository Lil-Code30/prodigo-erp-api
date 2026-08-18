package com.licode.prodigoerp.tenant.application.port.input;

public interface TenantLookUpUseCase {

    Boolean existsBySlug(String slug);
}
