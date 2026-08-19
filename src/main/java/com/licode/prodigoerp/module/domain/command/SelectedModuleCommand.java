package com.licode.prodigoerp.module.domain.command;

public record SelectedModuleCommand(
        Long moduleId,
        String moduleName,
        String moduleKey
) {
}
