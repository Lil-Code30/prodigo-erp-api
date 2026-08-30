package com.licode.prodigoerp.auth.domain.model;

import com.licode.prodigoerp.tenant.domain.model.Tenant;

import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class Role {

    private UUID id;
    private Tenant tenant;
    private String name;
    private String description;
    private Boolean isDefault;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
