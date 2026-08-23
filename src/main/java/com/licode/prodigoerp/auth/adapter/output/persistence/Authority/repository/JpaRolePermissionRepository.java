package com.licode.prodigoerp.auth.adapter.output.persistence.Authority.repository;

import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.RolePermissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRolePermissionRepository extends JpaRepository<RolePermissionJpaEntity, Long> {
}
