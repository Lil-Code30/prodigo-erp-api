package com.licode.prodigoerp.common.security;

import com.licode.prodigoerp.common.security.exception.JwtValidationException;
import com.licode.prodigoerp.auth.adapter.input.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration}")
    private long jwtExpiration;

    private SecretKey key;


    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate Access Token
     */
    public String generateAccessToken(
            UUID userId, String username, String email,
            UUID tenantId, String tenantSlug,
            List<String> roles, List<String> permissions
    ) {

        return Jwts.builder()
                .issuer("Prodigo_ERP_SYSTEM")
                .subject(userId.toString())
                .claim("type", "access")
                .claim("userId", userId)
                .claim("username", username)
                .claim("email", email)
                .claim("tenantId", tenantId)
                .claim("tenantSlug", tenantSlug)
                .claim("roles", roles)
                .claim("permissions", permissions)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {

        try {
            Claims claims = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token)
                    .getPayload();

            return String.valueOf(claims.get("username"));
        } catch (ExpiredJwtException e) {
            throw new JwtValidationException("Token expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtValidationException("Invalid token", e);
        }
    }

    public Long getUserIdFromToken(String token) {

        try {
            Claims claims = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.get("userId",  Long.class);
        } catch (ExpiredJwtException e) {
            throw new JwtValidationException("Token expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtValidationException("Invalid token", e);
        }
    }

    public Long getTenantIdFromToken(String token) {

        try {
            Claims claims = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.get("tenantId",  Long.class);
        } catch (ExpiredJwtException e) {
            throw new JwtValidationException("Token expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtValidationException("Invalid token", e);
        }
    }

    public boolean validateToken(String token) {
        try{

            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

            return true;

        }catch(Exception e){
            log.error("JWT validation error: {}", e.getMessage());
        }

        return false;
    }

    public long getExpirationSeconds() {
        return jwtExpiration / 1000;
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new JwtValidationException("Token expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtValidationException("Invalid token", e);
        }
    }

}
