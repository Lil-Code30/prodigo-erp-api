package com.licode.prodigoerp.module.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterSelectedModule(
        @NotNull Long moduleId,
        @NotBlank String moduleName,
        @NotBlank String moduleKey
) {
}
