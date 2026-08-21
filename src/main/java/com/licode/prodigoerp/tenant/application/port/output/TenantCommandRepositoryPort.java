package com.licode.prodigoerp.tenant.application.port.output;


import com.licode.prodigoerp.tenant.application.port.input.command.CreateTenantCommand;
import com.licode.prodigoerp.tenant.domain.model.Tenant;

public interface TenantCommandRepositoryPort {

      Tenant createNewTenant(CreateTenantCommand createTenantCommand);
//    Tenant updateTenant(Tenant tenant); TO BE TALK
}
