package com.licode.prodigoerp.auth.domain.model;

import lombok.Data;


import java.time.Instant;
import java.util.UUID;

@Data
public class UserRole {

    private UUID id;
    private User user;
    private Role role;
    private UUID tenantId;
    private String assignedBy;
    private Instant assignedAt;
    private Instant expiresAt;

}
