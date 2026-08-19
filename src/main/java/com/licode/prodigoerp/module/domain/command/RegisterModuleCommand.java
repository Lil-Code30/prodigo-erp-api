package com.licode.prodigoerp.module.domain.command;



import com.licode.prodigoerp.auth.domain.command.CreatePermissionCommand;

import java.math.BigDecimal;
import java.util.List;

public record RegisterModuleCommand(
        String name,
        String moduleKey,
        BigDecimal price,
        List<CreatePermissionCommand> createPermissions
) {
}
