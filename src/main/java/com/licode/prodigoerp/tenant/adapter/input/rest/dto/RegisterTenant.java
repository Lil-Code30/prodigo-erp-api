package com.licode.prodigoerp.tenant.adapter.input.rest.dto;

public record RegisterTenant(
        String companyName,
        String companySlug,
        String country,
        String username,
        String email,
        String password,
        String firstName,
        String lastName
) {
}
