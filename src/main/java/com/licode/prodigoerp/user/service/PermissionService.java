package com.licode.prodigoerp.user.service;

import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.common.exception.NotFoundException;
import com.licode.prodigoerp.common.security.SecurityUtils;
import com.licode.prodigoerp.common.security.dto.JwtPrincipal;
import com.licode.prodigoerp.module.entity.Module;
import com.licode.prodigoerp.module.repository.ModuleRepository;
import com.licode.prodigoerp.module.service.ModuleService;
import com.licode.prodigoerp.user.dto.AssignPermissionDto;
import com.licode.prodigoerp.user.dto.CreatePermission;
import com.licode.prodigoerp.user.entity.Permission;
import com.licode.prodigoerp.user.entity.Role;
import com.licode.prodigoerp.user.entity.RolePermission;
import com.licode.prodigoerp.user.mapper.PermissionMapper;
import com.licode.prodigoerp.user.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleService roleService;
    private final ModuleRepository moduleRepository;

    @Transactional
    public Permission createPermission(CreatePermission createPermission, String moduleKey) {
        // We got the module key, then we need to fetch the whole Module object to create the associate permission
        Optional<Module> module = moduleRepository.findModuleByModuleKey(moduleKey);

        if(module.isEmpty()) {
            throw  new NotFoundException("Module with key " + moduleKey +" not found");
        }

        String currentUser = SecurityUtils.getCurrentUsernameOrElseSysName();

        Permission permission = PermissionMapper.toEntity(createPermission, module.get(), currentUser);

        return permissionRepository.save(permission);
    }

    @Transactional
    public void assignPermissionToRole(AssignPermissionDto assignPermissionDto) {

        String currentUser = SecurityUtils.getCurrentUsernameOrElseSysName();

        Permission permission = permissionRepository.findPermissionByCode(assignPermissionDto.permissionCode());

        // with the data in the assignPermissionDto (roleName and tenantId)
        // we can fetch for the whole Role object and assign the permission to the role
        Role role = roleService.getRoleByRoleNameAndTenantId(assignPermissionDto.roleName(), assignPermissionDto.tenantId());

        RolePermission rolePermission = new RolePermission();

        rolePermission.setPermission(permission);
        rolePermission.setRole(role);
        rolePermission.setGrantedAt(Instant.now());
        rolePermission.setGrantedBy(currentUser);

        roleService.saveRolePermission(rolePermission);
    }

//    @Transactional
//    public void assignNPermissionToRole(List<String> permissionCodes, Role role) {
//
//    }
}
