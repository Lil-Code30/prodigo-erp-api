package com.licode.prodigoerp.tenant.domain.model;

import com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant.TenantJpaEntity;

import java.time.Instant;

public class TenantEntitlement {

    private Long id;
    private TenantJpaEntity tenantJpaEntity;
    private Integer maxUsers;
    private Integer maxStorageGb;
    private Long maxProducts;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
