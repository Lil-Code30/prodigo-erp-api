package com.licode.prodigoerp.tenant.adapter.output.persistence.TenantEntitlement;

import com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant.TenantJpaMapper;
import com.licode.prodigoerp.tenant.application.port.output.TenantEntitlementCreatePort;
import com.licode.prodigoerp.tenant.domain.model.Tenant;
import com.licode.prodigoerp.tenant.domain.model.TenantEntitlement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class TenantEntitlementPersistenceAdapter implements TenantEntitlementCreatePort {

    private final JpaTenantEntitlementRepository jpaTenantEntitlementRepository;


    @Override
    public TenantEntitlement createDefaultTenantEntitlement(TenantEntitlement tenantEntitlement) {
        TenantJpaEntitlement tenantJpaEntitlement = jpaTenantEntitlementRepository.save(TenantEntitlementJpaMapper.toDbCreateTenantEntitlement(tenantEntitlement));

        return TenantEntitlementJpaMapper.toDomainModel(tenantJpaEntitlement);
    }
}
