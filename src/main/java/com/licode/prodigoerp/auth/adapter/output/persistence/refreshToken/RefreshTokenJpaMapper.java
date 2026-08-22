package com.licode.prodigoerp.auth.adapter.output.persistence.refreshToken;

import com.licode.prodigoerp.auth.adapter.output.persistence.user.UserJpaMapper;
import com.licode.prodigoerp.auth.domain.model.RefreshToken;

public class RefreshTokenJpaMapper {

    public static RefreshTokenJpaEntity toJpaEntity(RefreshToken refreshToken) {

        RefreshTokenJpaEntity jpaEntity = new RefreshTokenJpaEntity();

        jpaEntity.setId(refreshToken.getId());
        jpaEntity.setToken(refreshToken.getToken());
        jpaEntity.setUserJpaEntity(UserJpaMapper.toJpaEntity(refreshToken.getUser()));
        jpaEntity.setExpiryDate(refreshToken.getExpiryDate());
        jpaEntity.setIsRevoked(refreshToken.getIsRevoked());
        jpaEntity.setCreatedAt(refreshToken.getCreatedAt());

        return  jpaEntity;
    }

    public static RefreshToken toDomainModel(RefreshTokenJpaEntity jpaEntity) {
        RefreshToken domainModel = new RefreshToken();

        domainModel.setId(jpaEntity.getId());
        domainModel.setToken(jpaEntity.getToken());
        domainModel.setUser(UserJpaMapper.toDomainModel(jpaEntity.getUserJpaEntity()));
        domainModel.setExpiryDate(jpaEntity.getExpiryDate());
        domainModel.setIsRevoked(jpaEntity.getIsRevoked());
        domainModel.setCreatedAt(jpaEntity.getCreatedAt());

        return domainModel;
    }
}
