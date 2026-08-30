package com.licode.prodigoerp.module.application.port.input.command;

import java.math.BigDecimal;
import java.util.UUID;

public record ShowPublicModuleCommand(
        UUID id,
         String name,
         String description,
         String moduleKey,
         BigDecimal price,
         String currency
) {
}
