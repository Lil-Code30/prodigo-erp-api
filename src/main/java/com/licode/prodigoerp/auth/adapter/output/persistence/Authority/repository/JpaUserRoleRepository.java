package com.licode.prodigoerp.auth.adapter.output.persistence.Authority.repository;

import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.UserRoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaUserRoleRepository extends JpaRepository<UserRoleJpaEntity, Long> {

    @Query("""
        SELECT r.name FROM UserRoleJpaEntity ur JOIN ur.role r
        WHERE ur.userJpaEntity.id = :userId
        AND (ur.expiresAt IS NULL OR ur.expiresAt > CURRENT_TIMESTAMP)
        """)
    List<String> findActiveRoleNamesByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT DISTINCT p.code FROM UserRoleJpaEntity ur
        JOIN ur.role r
        JOIN RolePermission rp ON rp.role = r
        JOIN rp.permission p
        WHERE ur.userJpaEntity.id = :userId
        AND (ur.expiresAt IS NULL OR ur.expiresAt > CURRENT_TIMESTAMP)
        """)
    List<String> findActivePermissionCodesByUserId(@Param("userId") Long userId);
}
