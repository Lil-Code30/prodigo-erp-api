package com.licode.prodigoerp.tenant.application.service;

import com.licode.prodigoerp.tenant.application.port.input.CreateTenantUseCase;
import com.licode.prodigoerp.tenant.application.port.output.TenantCommandRepositoryPort;
import com.licode.prodigoerp.tenant.application.port.input.command.CreateTenantCommand;
import com.licode.prodigoerp.tenant.domain.model.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantService implements CreateTenantUseCase {
    private final TenantCommandRepositoryPort tenantCommandRepositoryPort;

    @Override
    public Tenant create(CreateTenantCommand command) {

        return tenantCommandRepositoryPort.createNewTenant(command);
    }
}
