package com.licode.prodigoerp.module.dto;

import com.licode.prodigoerp.user.dto.CreatePermission;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record RegisterModule(
        @NotBlank @Size(max = 150) String name,
        @NotBlank @Size(max = 255) String moduleKey,
        @NotNull BigDecimal price,
        @NotNull @Valid List<CreatePermission> createPermissions
) {
}
