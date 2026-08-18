package com.licode.prodigoerp.tenant.application.port.output;

import com.licode.prodigoerp.tenant.domain.model.TenantEntitlement;

public interface TenantEntitlementCreatePort {

    TenantEntitlement createDefaultTenantEntitlement(TenantEntitlement tenantEntitlement);
}
