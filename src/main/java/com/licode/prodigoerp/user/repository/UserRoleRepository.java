package com.licode.prodigoerp.user.repository;

import com.licode.prodigoerp.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<String> findActiveRoleNameByUserId(Long userId);
    List<String> findActivePermissionCodesByUserId(Long userId);
}
