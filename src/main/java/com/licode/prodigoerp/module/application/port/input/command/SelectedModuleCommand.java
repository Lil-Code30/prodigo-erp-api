package com.licode.prodigoerp.module.application.port.input.command;

import java.util.UUID;

public record SelectedModuleCommand(
        UUID moduleId,
        String moduleName,
        String moduleKey
) {
}
