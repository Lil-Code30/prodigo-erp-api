package com.licode.prodigoerp.auth.adapter.input.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record RegisterRequestDto(
        @NotBlank String companyName,
        @NotBlank String companySlug,
        @NotBlank String country,
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        List<RegisterSelectedModuleDto> selectedModules
) {
}
