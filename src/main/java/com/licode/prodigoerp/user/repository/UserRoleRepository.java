package com.licode.prodigoerp.user.repository;

import com.licode.prodigoerp.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("""
        SELECT r.name FROM UserRole ur JOIN ur.role r
        WHERE ur.user.id = :userId
        AND (ur.expiresAt IS NULL OR ur.expiresAt > CURRENT_TIMESTAMP)
        """)
    List<String> findActiveRoleNamesByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT DISTINCT p.code FROM UserRole ur 
        JOIN ur.role r 
        JOIN RolePermission rp ON rp.role = r 
        JOIN rp.permission p 
        WHERE ur.user.id = :userId 
        AND (ur.expiresAt IS NULL OR ur.expiresAt > CURRENT_TIMESTAMP)        
        """)
    List<String> findActivePermissionCodesByUserId(@Param("userId") Long userId);
}
