package com.licode.prodigoerp.auth.application.port.output;

import com.licode.prodigoerp.auth.domain.model.Permission;
import com.licode.prodigoerp.auth.domain.model.RolePermission;

public interface SavePermissionPort {

    Permission savePermission(Permission permission);
    void assignPermissionToRole(RolePermission rolePermission);
}
