package com.licode.prodigoerp.auth.adapter.output.persistence.Authority.mapper;

import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.RolePermissionJpaEntity;
import com.licode.prodigoerp.auth.domain.model.RolePermission;

public class RolePermissionJpaMapper {

    public static RolePermissionJpaEntity toJpaEntity(RolePermission rolePermission) {
        RolePermissionJpaEntity rolePermissionJpaEntity = new RolePermissionJpaEntity();

        rolePermissionJpaEntity.setId(rolePermission.getId());
        rolePermissionJpaEntity.setRoleJpaEntity(RoleJpaMapper.toJpaEntity(rolePermission.getRole()));
        rolePermissionJpaEntity.setPermissionJpaEntity(PermissionJpaMapper.toJpaEntity(rolePermission.getPermission()));
        rolePermissionJpaEntity.setGrantedAt(rolePermission.getGrantedAt());
        rolePermissionJpaEntity.setGrantedBy(rolePermission.getGrantedBy());

        return rolePermissionJpaEntity;
    }

    public static RolePermission toDomainModel(RolePermissionJpaEntity rolePermissionJpaEntity) {
       RolePermission rolePermission = new RolePermission();

        rolePermission.setId(rolePermissionJpaEntity.getId());
        rolePermission.setRole(RoleJpaMapper.toDomainModel(rolePermissionJpaEntity.getRoleJpaEntity()));
        rolePermission.setPermission(PermissionJpaMapper.toDomainModel(rolePermissionJpaEntity.getPermissionJpaEntity()));
        rolePermission.setGrantedAt(rolePermissionJpaEntity.getGrantedAt());
        rolePermission.setGrantedBy(rolePermissionJpaEntity.getGrantedBy());

        return rolePermission;
    }
}
