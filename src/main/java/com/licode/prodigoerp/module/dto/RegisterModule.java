package com.licode.prodigoerp.module.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RegisterModule(
        @NotBlank @Size(max = 150) String name,
        @NotBlank @Size(max = 255) String moduleKey,
        @NotNull BigDecimal price
) {
}
