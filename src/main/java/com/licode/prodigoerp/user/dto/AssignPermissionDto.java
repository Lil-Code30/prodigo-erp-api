package com.licode.prodigoerp.user.dto;

import jakarta.validation.constraints.NotBlank;

public record AssignPermissionDto(
        @NotBlank String permissionCode,
        @NotBlank String roleName,
        @NotBlank Long tenantId
) {
}
