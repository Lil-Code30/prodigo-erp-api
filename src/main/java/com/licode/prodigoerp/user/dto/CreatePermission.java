package com.licode.prodigoerp.user.dto;

import com.licode.prodigoerp.module.entity.Module;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePermission(
        String description,
        @NotBlank @Size(max = 50) String action,
        @NotBlank @Size(max = 100) String resource,
        @Nullable Module module
        ) {
}
