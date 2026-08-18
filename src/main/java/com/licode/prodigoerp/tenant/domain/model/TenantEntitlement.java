package com.licode.prodigoerp.tenant.domain.model;

import lombok.Data;

import java.time.Instant;

@Data
public class TenantEntitlement {

    private Long id;
    private Tenant tenant;
    private Integer maxUsers;
    private Integer maxStorageGb;
    private Long maxProducts;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
