package com.licode.prodigoerp.common.security;

import com.licode.prodigoerp.common.exception.JwtValidationException;
import com.licode.prodigoerp.user.entity.User;
import com.licode.prodigoerp.user.entity.UserPrincipal;
import com.licode.prodigoerp.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    public String generateAccessToken(UserPrincipal principal) {

        var user = principal.getUser();

        // Going to loop through the authorities, then find all authorities starting with "ROLE_"
        List<String> roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).filter(Objects::nonNull)
                .filter(a -> a.startsWith("ROLE_"))
                .map(a -> a.substring(5))
                .toList();

        List<String> permissions = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).filter(Objects::nonNull)
                .filter(a -> a.startsWith("PERM_"))
                .map(a -> a.substring(5))
                .toList();

        return Jwts.builder()
                .issuer("Prodigo")
                .subject(String.valueOf(user.getId()))
                .claim("type", "access")
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("tenantId", user.getTenant() != null ? user.getTenant().getId() : null)
                .claim("tenantSlug", user.getTenant() != null ? user.getTenant().getSlug() : null)
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
