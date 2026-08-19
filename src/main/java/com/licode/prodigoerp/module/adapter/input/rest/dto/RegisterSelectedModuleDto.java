package com.licode.prodigoerp.module.adapter.input.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterSelectedModuleDto(
        @NotNull Long moduleId,
        @NotBlank String moduleName,
        @NotBlank String moduleKey
) {
}
