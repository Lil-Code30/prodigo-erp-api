package com.licode.prodigoerp.tenant.domain.command;

public record CreateTenantCommand(
        String companyName,
        String companySlug,
        String country
) {
}
