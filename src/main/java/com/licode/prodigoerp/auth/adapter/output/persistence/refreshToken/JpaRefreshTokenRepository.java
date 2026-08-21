package com.licode.prodigoerp.auth.adapter.output.persistence.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {
    Optional<RefreshTokenJpaEntity> findByToken(String token);
    Optional<RefreshTokenJpaEntity> findByUser_Id(Long userId);
}
