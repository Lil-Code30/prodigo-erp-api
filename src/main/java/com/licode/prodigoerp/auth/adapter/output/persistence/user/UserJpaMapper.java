package com.licode.prodigoerp.auth.adapter.output.persistence.user;

import com.licode.prodigoerp.auth.domain.model.User;
import com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant.TenantJpaMapper;

public class UserJpaMapper {

    public static UserJpaEntity toJpaEntity(User user) {
        UserJpaEntity userJpaEntity = new UserJpaEntity();

        userJpaEntity.setId(user.getId());
        userJpaEntity.setUsername(user.getUsername());
        userJpaEntity.setPassword(user.getPassword()); // NOTE: Hash password
        userJpaEntity.setEmail(user.getEmail());
        userJpaEntity.setFirstName(user.getFirstName());
        userJpaEntity.setLastName(user.getLastName());
        userJpaEntity.setTenant(TenantJpaMapper.toJpaEntity(user.getTenant()));
        userJpaEntity.setStatus(user.getStatus());
        userJpaEntity.setIsSuperAdmin(user.getIsSuperAdmin());
        userJpaEntity.setLastLogin(user.getLastLogin());
        userJpaEntity.setCreatedAt(user.getCreatedAt());
        userJpaEntity.setUpdatedAt(user.getUpdatedAt());
        userJpaEntity.setCreatedBy(user.getCreatedBy());
        userJpaEntity.setUpdatedBy(user.getUpdatedBy());

        return userJpaEntity;
    }

    // TODO :
    public static User toDbCreateUser(){
        return new User();
    }

    public static User toDomainModel(UserJpaEntity userJpaEntity) {
        User user = new User();
        user.setId(userJpaEntity.getId());
        user.setUsername(userJpaEntity.getUsername());
        user.setPassword(userJpaEntity.getPassword());
        user.setEmail(userJpaEntity.getEmail());
        user.setFirstName(userJpaEntity.getFirstName());
        user.setLastName(userJpaEntity.getLastName());
        user.setTenant(TenantJpaMapper.toDomainModel(userJpaEntity.getTenant()));
        user.setStatus(userJpaEntity.getStatus());
        user.setIsSuperAdmin(userJpaEntity.getIsSuperAdmin());
        user.setLastLogin(userJpaEntity.getLastLogin());
        user.setCreatedAt(userJpaEntity.getCreatedAt());
        user.setUpdatedAt(userJpaEntity.getUpdatedAt());
        user.setCreatedBy(userJpaEntity.getCreatedBy());
        user.setUpdatedBy(userJpaEntity.getUpdatedBy());

        return user;
    }
}
