package com.licode.prodigoerp.module.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterSelectedModule(
        @NotBlank Long moduleId,
        @NotBlank String moduleName,
        @NotBlank String moduleKey
) {
}
