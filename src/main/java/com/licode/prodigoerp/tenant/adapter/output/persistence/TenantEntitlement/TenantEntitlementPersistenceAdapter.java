package com.licode.prodigoerp.tenant.adapter.output.persistence.TenantEntitlement;

import com.licode.prodigoerp.tenant.application.port.output.TenantEntitlementCreatePort;
import com.licode.prodigoerp.tenant.domain.model.TenantEntitlement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@RequiredArgsConstructor
public class TenantEntitlementPersistenceAdapter implements TenantEntitlementCreatePort {

    private final JpaTenantEntitlementRepository jpaTenantEntitlementRepository;


    @Override
    @Transactional
    public TenantEntitlement createDefaultTenantEntitlement(TenantEntitlement tenantEntitlement) {
        TenantEntitlementJpaEntity tenantEntitlementJpaEntity = jpaTenantEntitlementRepository.save(TenantEntitlementJpaMapper.toDbCreateTenantEntitlement(tenantEntitlement));

        return TenantEntitlementJpaMapper.toDomainModel(tenantEntitlementJpaEntity);
    }
}
