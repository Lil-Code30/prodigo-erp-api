package com.licode.prodigoerp.tenant.repository;

import com.licode.prodigoerp.tenant.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
