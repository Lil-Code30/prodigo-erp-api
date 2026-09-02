package com.licode.prodigoerp.auth.adapter.input.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateSuperAdminDto(
        @NotBlank  String username,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName
) {
}
