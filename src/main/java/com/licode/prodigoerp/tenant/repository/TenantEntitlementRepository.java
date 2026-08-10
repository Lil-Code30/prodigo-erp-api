package com.licode.prodigoerp.tenant.repository;

import com.licode.prodigoerp.tenant.entity.TenantEntitlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantEntitlementRepository extends JpaRepository<TenantEntitlement, Long> {
}
