package com.licode.prodigoerp.auth.domain.command;

public record SelectedModuleCommand(
        Long moduleId,
        String moduleName,
        String moduleKey
) {
}
