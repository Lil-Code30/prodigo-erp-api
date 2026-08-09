package com.licode.prodigoerp.user.mapper;

import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.user.dto.CreatePermission;
import com.licode.prodigoerp.user.entity.Permission;

import java.time.Instant;

public class PermissionMapper {

    public static Permission toEntity(CreatePermission createPermission, String actor) {
        Permission permission = new Permission();

        String permissionCode = createPermission.resource() + "_" + createPermission.action();
        permission.setCode(permissionCode);
        permission.setDescription(createPermission.description());
        permission.setAction(createPermission.action());
        permission.setResource(createPermission.resource());
        permission.setModule(createPermission.module());

        permission.setCreatedAt(Instant.now());
        permission.setUpdatedAt(Instant.now());
        permission.setCreatedBy(actor);
        permission.setUpdatedBy(actor);


        return permission;
    }
}
