package com.licode.prodigoerp.tenant.application.port.input;

import com.licode.prodigoerp.tenant.domain.model.Tenant;
import com.licode.prodigoerp.tenant.domain.model.TenantEntitlement;

public interface TenantEntitlementUseCase {

    TenantEntitlement createDefaultTenantEntitlement(Tenant tenant);
}
