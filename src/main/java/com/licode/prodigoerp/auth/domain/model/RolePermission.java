package com.licode.prodigoerp.auth.domain.model;


import lombok.Data;

import java.time.Instant;

@Data
public class RolePermission {

    private Long id;
    private Role role;
    private Permission permission;
    private Instant grantedAt;
    private String grantedBy;
}
