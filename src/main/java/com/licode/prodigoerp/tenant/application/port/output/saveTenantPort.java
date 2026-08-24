package com.licode.prodigoerp.tenant.application.port.output;


import com.licode.prodigoerp.tenant.domain.model.Tenant;

public interface saveTenantPort {


      Tenant createTenant(Tenant tenant);
}
