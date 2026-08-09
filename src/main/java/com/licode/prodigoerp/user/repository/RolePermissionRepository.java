package com.licode.prodigoerp.user.repository;

import com.licode.prodigoerp.user.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
