package com.licode.prodigoerp.tenant.domain.model;


import lombok.Data;

import java.time.Instant;

@Data
public class Tenant {
    private Long id;
    private String name;
    private String slug;
    private String country;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
