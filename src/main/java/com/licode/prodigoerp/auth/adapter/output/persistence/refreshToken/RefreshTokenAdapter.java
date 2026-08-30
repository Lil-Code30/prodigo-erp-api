package com.licode.prodigoerp.auth.adapter.output.persistence.refreshToken;

import com.licode.prodigoerp.auth.adapter.output.persistence.user.UserJpaMapper;
import com.licode.prodigoerp.auth.application.port.output.RefreshTokenStorePort;
import com.licode.prodigoerp.auth.domain.model.RefreshToken;
import com.licode.prodigoerp.auth.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RefreshTokenAdapter implements RefreshTokenStorePort {

    @Value("${security.jwt.refresh-expiration}")
    private long refreshExpirationMs;

    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;
    private static final SecureRandom secureRandom = new SecureRandom();

    @Override
    public Optional<RefreshToken> findRefreshTokenByTokenString(String tokenString) {
        return jpaRefreshTokenRepository
                .findByToken(tokenString)
                .map(RefreshTokenJpaMapper::toDomainModel);
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenByUser_Id(UUID userId) {
        Optional<RefreshTokenJpaEntity> refreshTokenJpaEntity = jpaRefreshTokenRepository.findByUserJpaEntity_Id(userId);

        return refreshTokenJpaEntity.map(RefreshTokenJpaMapper::toDomainModel);
    }

    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        RefreshTokenJpaEntity refreshTokenJpaEntity = new RefreshTokenJpaEntity();

        // Generate a secure random string using Java's SecureRandom
        byte[] randomBytes = new byte[64]; // 512 bits
        secureRandom.nextBytes(randomBytes);

        String generateOpaqueToken =  Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        refreshTokenJpaEntity.setUserJpaEntity(UserJpaMapper.toJpaEntity(user));
        refreshTokenJpaEntity.setToken(generateOpaqueToken);
        refreshTokenJpaEntity.setIsRevoked(false);
        refreshTokenJpaEntity.setExpiryDate(Instant.now().plusSeconds(refreshExpirationMs));
        refreshTokenJpaEntity.setCreatedAt(Instant.now());

        RefreshTokenJpaEntity createJpaRefreshToken = jpaRefreshTokenRepository.save(refreshTokenJpaEntity);

        return RefreshTokenJpaMapper.toDomainModel(createJpaRefreshToken);
    }

    @Override
    @Transactional
    public void revokeRefreshTokenByTokenString(String tokenString) {
        jpaRefreshTokenRepository.findByToken(tokenString)
                .ifPresent(rt -> {
                    rt.setIsRevoked(true);
                    jpaRefreshTokenRepository.save(rt);
                });

    }
}
