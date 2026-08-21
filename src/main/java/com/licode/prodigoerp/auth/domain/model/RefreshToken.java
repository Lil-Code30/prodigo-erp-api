package com.licode.prodigoerp.auth.domain.model;



import java.time.Instant;

public class RefreshToken {
    private Long id;
    private String token;
    private User user;
    private Instant expiryDate;
    private Boolean isRevoked;
    private Instant createdAt;
}
