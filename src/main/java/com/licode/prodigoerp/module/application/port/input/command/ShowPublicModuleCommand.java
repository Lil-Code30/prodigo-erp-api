package com.licode.prodigoerp.module.application.port.input.command;

import java.math.BigDecimal;

public record ShowPublicModuleCommand(
         Long id,
         String name,
         String moduleKey,
         BigDecimal price,
         String currency
) {
}
