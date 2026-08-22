package com.licode.prodigoerp.auth.adapter.output.persistence.Authority.mapper;

import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.RoleJpaEntity;
import com.licode.prodigoerp.auth.domain.model.Role;
import com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant.TenantJpaMapper;

public class RoleJpaMapper {

    public static RoleJpaEntity toJpaEntity(Role role){
        RoleJpaEntity roleJpaEntity = new RoleJpaEntity();

        roleJpaEntity.setId(role.getId());
        roleJpaEntity.setTenantJpaEntity(TenantJpaMapper.toJpaEntity(role.getTenant()));
        roleJpaEntity.setName(role.getName());
        roleJpaEntity.setDescription(role.getDescription());
        roleJpaEntity.setIsDefault(role.getIsDefault());
        roleJpaEntity.setCreatedAt(role.getCreatedAt());
        roleJpaEntity.setUpdatedAt(role.getUpdatedAt());
        roleJpaEntity.setCreatedBy(role.getCreatedBy());
        roleJpaEntity.setUpdatedBy(role.getUpdatedBy());

        return roleJpaEntity;
    }

    public static Role toDomainModel(RoleJpaEntity roleJpaEntity){
        Role role = new Role();

        role.setId(roleJpaEntity.getId());
        role.setTenant(TenantJpaMapper.toDomainModel(roleJpaEntity.getTenantJpaEntity()));
        role.setName(roleJpaEntity.getName());
        role.setDescription(roleJpaEntity.getDescription());
        role.setIsDefault(roleJpaEntity.getIsDefault());
        role.setCreatedAt(roleJpaEntity.getCreatedAt());
        role.setUpdatedAt(roleJpaEntity.getUpdatedAt());
        role.setCreatedBy(roleJpaEntity.getCreatedBy());
        role.setUpdatedBy(roleJpaEntity.getUpdatedBy());

        return role;
    }
}
