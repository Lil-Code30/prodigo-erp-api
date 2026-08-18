package com.licode.prodigoerp.tenant.adapter.input.rest.dto;

public record RegisterTenantDto(
        String companyName,
        String companySlug,
        String country
) {
}
