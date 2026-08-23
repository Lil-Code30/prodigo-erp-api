package com.licode.prodigoerp.auth.application.port.output;

import com.licode.prodigoerp.auth.domain.model.Role;
import com.licode.prodigoerp.auth.domain.model.UserRole;

public interface SaveRolePort {
    // NOTE: We will manage the saving of the
    // ROlE, USERROLE

    Role saveRole(Role role);

    void saveUserRole(UserRole userRole);
}
