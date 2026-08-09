package com.licode.prodigoerp.user.service;

import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.user.dto.CreatePermission;
import com.licode.prodigoerp.user.entity.Permission;
import com.licode.prodigoerp.user.entity.Role;
import com.licode.prodigoerp.user.entity.RolePermission;
import com.licode.prodigoerp.user.mapper.PermissionMapper;
import com.licode.prodigoerp.user.repository.PermissionRepository;
import com.licode.prodigoerp.user.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Transactional
    public Permission createPermission(CreatePermission createPermission) {
        Permission permission = PermissionMapper.toEntity(createPermission, SystemConstants.SYSTEM_NAME);

        return permissionRepository.save(permission);
    }

    @Transactional
    public RolePermission assignPermissionToRole(Permission permission, Role role) {

        RolePermission rolePermission = new RolePermission();

        rolePermission.setPermission(permission);
        rolePermission.setRole(role);
        rolePermission.setGrantedAt(Instant.now());
        rolePermission.setGrantedBy(SystemConstants.SYSTEM_NAME);

        return rolePermissionRepository.save(rolePermission);
    }
}
