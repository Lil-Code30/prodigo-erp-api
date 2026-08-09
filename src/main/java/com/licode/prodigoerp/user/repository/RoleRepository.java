package com.licode.prodigoerp.user.repository;

import com.licode.prodigoerp.user.entity.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNameAndTenant_Id(String name, Long tenantId);

    boolean existsByName(String roleName);

    Optional<Role> findRoleByName(String name);
}
