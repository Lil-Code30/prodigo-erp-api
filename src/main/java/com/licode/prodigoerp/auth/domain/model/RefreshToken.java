package com.licode.prodigoerp.auth.domain.model;



import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class RefreshToken {
    private UUID id;
    private String token;
    private User user;
    private Instant expiryDate;
    private Boolean isRevoked;
    private Instant createdAt;
}
