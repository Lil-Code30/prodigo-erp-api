package com.licode.prodigoerp.tenant.adapter.output.persistence.TenantEntitlement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTenantEntitlementRepository extends JpaRepository<TenantEntitlementJpaEntity, Long> {
}
