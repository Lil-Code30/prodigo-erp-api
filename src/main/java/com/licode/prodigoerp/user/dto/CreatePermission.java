package com.licode.prodigoerp.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePermission(
        @NotNull @Size(max = 500) String description,
        @NotBlank @Size(max = 50) String action,
        @NotBlank @Size(max = 100) String resource
        ) {
}
