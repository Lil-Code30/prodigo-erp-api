package com.licode.prodigoerp.auth.domain.model;



import lombok.Data;

import java.time.Instant;

@Data
public class RefreshToken {
    private Long id;
    private String token;
    private User user;
    private Instant expiryDate;
    private Boolean isRevoked;
    private Instant createdAt;
}
