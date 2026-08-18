package com.licode.prodigoerp.tenant.application.service;

import com.licode.prodigoerp.tenant.application.port.input.TenantLookUpUseCase;
import com.licode.prodigoerp.tenant.application.port.output.TenantQueryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantLookUpService implements TenantLookUpUseCase {
    private final TenantQueryRepositoryPort tenantQueryRepositoryPort;


    @Override
    public Boolean existsBySlug(String slug) {
        return tenantQueryRepositoryPort.existsBySlug(slug);
    }
}
