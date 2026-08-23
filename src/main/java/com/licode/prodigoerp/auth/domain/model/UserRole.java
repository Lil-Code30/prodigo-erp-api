package com.licode.prodigoerp.auth.domain.model;

import lombok.Data;


import java.time.Instant;

@Data
public class UserRole {

    private Long id;
    private User user;
    private Role role;
    private Long tenantId;
    private String assignedBy;
    private Instant assignedAt;
    private Instant expiresAt;

}
