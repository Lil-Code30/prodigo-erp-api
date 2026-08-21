package com.licode.prodigoerp.tenant.application.port.input;

import com.licode.prodigoerp.tenant.application.port.input.command.CreateTenantCommand;
import com.licode.prodigoerp.tenant.domain.model.Tenant;

public interface CreateTenantUseCase {

    Tenant create(CreateTenantCommand command);
}
