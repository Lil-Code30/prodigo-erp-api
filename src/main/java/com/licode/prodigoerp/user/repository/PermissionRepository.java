package com.licode.prodigoerp.user.repository;

import com.licode.prodigoerp.user.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
