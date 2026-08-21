package com.licode.prodigoerp.tenant.application.service;

import com.licode.prodigoerp.common.exception.NotFoundException;
import com.licode.prodigoerp.tenant.application.port.input.TenantLookUpUseCase;
import com.licode.prodigoerp.tenant.application.port.output.TenantQueryRepositoryPort;
import com.licode.prodigoerp.tenant.domain.model.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantLookUpService implements TenantLookUpUseCase {

    private final TenantQueryRepositoryPort tenantQueryRepositoryPort;


    @Override
    public Boolean existsBySlug(String slug) {
        return tenantQueryRepositoryPort.existsBySlug(slug);
    }

    @Override
    public Tenant findTenantById(Long tenantId) {
        Optional<Tenant> tenant = tenantQueryRepositoryPort.findTenantById(tenantId);

        if(tenant.isEmpty()){
            throw new NotFoundException("Tenant not found with id " + tenantId);
        }

        return tenant.get();
    }


}
