package com.licode.prodigoerp.common.security;

import com.licode.prodigoerp.user.entity.User;
import com.licode.prodigoerp.user.entity.UserPrincipal;
import com.licode.prodigoerp.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${security.jwt.refresh-expiration}")
    private long refreshExpiration;

    private SecretKey key;

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate Access Token
     */
    public String generateToken(User user) {
        return Jwts.builder().issuer("Prodigo")
                .subject("Prodigo API JWT")
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("tenantId", user.getTenant().getId())
                .claim("tenantSlug" , user.getTenant().getSlug())
                .issuedAt(new Date())
                .expiration(new Date( System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();
    }

    /**
     * Generate Refresh Token
     */
    public String generateRefreshToken(Authentication authentication) {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal()

        List<String> roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith("ROLE_"))
                .map(a -> a.substring(5))
                .toList();

        List<String> permissions = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith("PERM_"))
                .map(a -> a.substring(5))
                .toList();

        return Jwts.builder().issuer("Prodigo")
                .subject("Prodigo Refresh Token")
                .claim("userId", user.getId())
                .claim("roles", roles)
                .claim("permissions", permissions)
                .claim("tenantId", user.getTenant().getId())
                .claim("tenantSlug" , user.getTenant().getSlug())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload();

        return String.valueOf(claims.get("username"));
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("userId",  Long.class);
    }

    public Long getTenantIdFromToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("tenantId",  Long.class);
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

}
