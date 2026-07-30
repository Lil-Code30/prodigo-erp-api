package com.licode.prodigoerp.user.repository;

import com.licode.prodigoerp.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNameAndTenant_Id(String name, Long tenantId);
}
