package com.licode.prodigoerp.module.adapter.input.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ShowPublicModuleDto(
        @NotNull Long id,
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String moduleKey,
        @NotBlank BigDecimal price,
        @NotBlank String currency
) {
}
