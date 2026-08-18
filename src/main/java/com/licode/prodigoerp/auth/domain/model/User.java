package com.licode.prodigoerp.auth.domain.model;


import com.licode.prodigoerp.tenant.domain.model.Tenant;
import lombok.Data;


import java.time.Instant;

@Data
public class User {
    private Long id;
    private String username;
    private Tenant tenant;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String status;
    private Boolean isSuperAdmin;
    private Instant lastLogin;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
