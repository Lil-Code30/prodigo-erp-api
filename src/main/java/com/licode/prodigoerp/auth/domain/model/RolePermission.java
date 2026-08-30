package com.licode.prodigoerp.auth.domain.model;


import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class RolePermission {

    private UUID id;
    private Role role;
    private Permission permission;
    private Instant grantedAt;
    private String grantedBy;
}
