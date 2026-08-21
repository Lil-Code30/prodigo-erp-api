package com.licode.prodigoerp.module.application.port.input.command;

public record SelectedModuleCommand(
        Long moduleId,
        String moduleName,
        String moduleKey
) {
}
