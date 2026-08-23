package com.licode.prodigoerp.auth.adapter.output.persistence.Authority.mapper;

import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.UserRoleJpaEntity;
import com.licode.prodigoerp.auth.adapter.output.persistence.user.UserJpaMapper;
import com.licode.prodigoerp.auth.domain.model.UserRole;

public class UserRoleJpaMapper {

    public static UserRoleJpaEntity toJpaEntity(UserRole userRole) {
        UserRoleJpaEntity userRoleJpaEntity = new UserRoleJpaEntity();

        userRoleJpaEntity.setId(userRole.getId());
        userRoleJpaEntity.setUserJpaEntity(UserJpaMapper.toJpaEntity(userRole.getUser()));
        userRoleJpaEntity.setRoleJpaEntity(RoleJpaMapper.toJpaEntity(userRole.getRole()));
        userRoleJpaEntity.setTenantId(userRole.getTenantId());
        userRoleJpaEntity.setAssignedBy(userRole.getAssignedBy());
        userRoleJpaEntity.setAssignedAt(userRole.getAssignedAt());
        userRoleJpaEntity.setExpiresAt(userRole.getExpiresAt());

        return userRoleJpaEntity;
    }

    public static UserRole toDomainModel(UserRoleJpaEntity userRoleJpaEntity) {
        UserRole userRole = new UserRole();

        userRole.setId(userRoleJpaEntity.getId());
        userRole.setUser(UserJpaMapper.toDomainModel(userRoleJpaEntity.getUserJpaEntity()));
        userRole.setRole(RoleJpaMapper.toDomainModel(userRoleJpaEntity.getRoleJpaEntity()));
        userRole.setTenantId(userRoleJpaEntity.getTenantId());
        userRole.setAssignedBy(userRoleJpaEntity.getAssignedBy());
        userRole.setAssignedAt(userRoleJpaEntity.getAssignedAt());
        userRole.setExpiresAt(userRoleJpaEntity.getExpiresAt());

        return userRole;
    }
}
