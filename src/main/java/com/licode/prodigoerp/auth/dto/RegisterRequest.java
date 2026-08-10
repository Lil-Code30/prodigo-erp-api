package com.licode.prodigoerp.auth.dto;

import com.licode.prodigoerp.module.dto.RegisterSelectedModule;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RegisterRequest(
        @NotBlank @Size(max = 255) String companyName,
        @NotBlank @Size(max = 255) String companySlug,
        @NotBlank @Size(max = 50) String country,
        @NotBlank @Size(max = 20) String username,
        @NotBlank @Email @Size(max = 150) String email,
        @NotBlank @Size(min = 8, max = 100) String password,
        @NotBlank @Size(max = 150) String firstName,
        @NotBlank @Size(max = 150) String lastName,
        @NotBlank List<RegisterSelectedModule> selectedModules
) {}
