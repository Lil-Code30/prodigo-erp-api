package com.licode.prodigoerp.auth.application.port.output;

import com.licode.prodigoerp.auth.domain.model.Permission;
import com.licode.prodigoerp.auth.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleQueryPort {
    // NOTE : WE will manage the query of the ROLE, USERROLE and PERMISSION here (all the queries)

    List<String> findActiveRoleNames(Long userId);
    List<String> findActivePermissionCodes(Long userId);
    Optional<Role> findRoleByIdAndTenantId(Long roleId, Long tenantId);
    Optional<Permission> findPermissionById(Long permissionId);
}
