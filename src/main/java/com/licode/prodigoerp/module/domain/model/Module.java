package com.licode.prodigoerp.module.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Data
public class Module {

    private UUID id;
    private String name;
    private String description;
    private String moduleKey;
    private BigDecimal price;
    private String currency;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
