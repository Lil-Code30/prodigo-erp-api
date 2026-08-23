package com.licode.prodigoerp.auth.adapter.output.persistence.Authority.repository;

import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.PermissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPermissionRepository extends JpaRepository<PermissionJpaEntity, Long> {

    Optional<PermissionJpaEntity> findPermissionJpaEntityById(Long permissionId);
}
