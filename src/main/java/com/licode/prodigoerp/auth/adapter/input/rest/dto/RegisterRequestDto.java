package com.licode.prodigoerp.auth.adapter.input.rest.dto;

import java.util.List;

public record RegisterRequestDto(
        String companyName,
        String companySlug,
        String country,
        String username,
        String email,
        String password,
        String firstName,
        String lastName,
        List<RegisterSelectedModuleDto> selectedModules
) {
}
