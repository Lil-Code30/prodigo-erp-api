package com.licode.prodigoerp.tenant.adapter.output.persistence.TenantEntitlement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTenantEntitlementRepository extends JpaRepository<TenantEntitlementJpaEntity, UUID> {
}
