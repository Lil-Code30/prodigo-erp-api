package com.licode.prodigoerp.auth.service;

import com.licode.prodigoerp.auth.entity.RefreshToken;
import com.licode.prodigoerp.auth.repository.RefreshTokenRepository;
import com.licode.prodigoerp.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private static final SecureRandom secureRandom = new SecureRandom();


    @Value("${security.jwt.refresh-expiration}")
    private long refreshExpirationMs;

    @Transactional
    public RefreshToken issueFor(User user) {
       RefreshToken refreshToken = refreshTokenRepository.findByUser_Id(user.getId())
                .orElseGet(RefreshToken::new);

       refreshToken.setUser(user);
       refreshToken.setToken(generateOpaqueToken());
       refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
       refreshToken.setIsRevoked(false);
       refreshToken.setCreatedAt(Instant.now());

       return refreshTokenRepository.save(refreshToken);
    }


    @Transactional
    public void revoke(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresent(rt -> {
                    rt.setIsRevoked(true);
                    refreshTokenRepository.save(rt);
                });
    }

    // Generate a secure random string using Java's SecureRandom
    private static String generateOpaqueToken() {
        byte[] randomBytes = new byte[64]; // 512 bits
        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }
}
