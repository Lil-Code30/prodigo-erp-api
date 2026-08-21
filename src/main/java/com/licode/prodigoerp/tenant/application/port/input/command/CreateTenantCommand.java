package com.licode.prodigoerp.tenant.application.port.input.command;

public record CreateTenantCommand(
        String companyName,
        String companySlug,
        String country
) {
}
