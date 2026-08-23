package com.licode.prodigoerp.auth.adapter.output.persistence.Authority;

import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.PermissionJpaEntity;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.RoleJpaEntity;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.RolePermissionJpaEntity;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.UserRoleJpaEntity;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.mapper.PermissionJpaMapper;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.mapper.RoleJpaMapper;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.mapper.RolePermissionJpaMapper;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.mapper.UserRoleJpaMapper;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.repository.JpaPermissionRepository;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.repository.JpaRolePermissionRepository;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.repository.JpaRoleRepository;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.repository.JpaUserRoleRepository;
import com.licode.prodigoerp.auth.application.port.output.RoleQueryPort;
import com.licode.prodigoerp.auth.application.port.output.SavePermissionPort;
import com.licode.prodigoerp.auth.application.port.output.SaveRolePort;
import com.licode.prodigoerp.auth.domain.model.Permission;
import com.licode.prodigoerp.auth.domain.model.Role;
import com.licode.prodigoerp.auth.domain.model.RolePermission;
import com.licode.prodigoerp.auth.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RoleQueryPort, SaveRolePort, SavePermissionPort {
    final private JpaRoleRepository jpaRoleRepository;
    final private JpaUserRoleRepository jpaUserRoleRepository;
    final private JpaPermissionRepository jpaPermissionRepository;
    final private JpaRolePermissionRepository jpaRolePermissionRepository;

    @Override
    public List<String> findActiveRoleNames(Long userId) {

        return jpaUserRoleRepository.findActiveRoleNamesByUserId(userId);
    }

    @Override
    public List<String> findActivePermissionCodes(Long userId) {
        return jpaUserRoleRepository.findActivePermissionCodesByUserId(userId);
    }

    @Override
    public Optional<Role> findRoleByIdAndTenantId(Long roleId, Long tenantId) {
        Optional<RoleJpaEntity> roleJpaEntity =  jpaRoleRepository.findRoleJpaEntitiesByIdAndTenantJpaEntity_Id(roleId, tenantId);

        return roleJpaEntity.map(RoleJpaMapper::toDomainModel);
    }

    @Override
    public Optional<Permission> findPermissionById(Long permissionId) {
       Optional<PermissionJpaEntity> permissionJpaEntity = jpaPermissionRepository
               .findPermissionJpaEntityById(permissionId);

       return permissionJpaEntity.map(PermissionJpaMapper::toDomainModel);
    }

    @Override
    @Transactional
    public Role saveRole(Role role) {
        RoleJpaEntity roleJpaEntity = jpaRoleRepository.save(
                RoleJpaMapper.toJpaEntity(role)
        );

        return RoleJpaMapper.toDomainModel(roleJpaEntity);
    }

    @Override
    @Transactional
    public void saveUserRole(UserRole userRole) {

        UserRoleJpaEntity userRoleJpaEntity =  jpaUserRoleRepository.save(UserRoleJpaMapper.toJpaEntity(userRole));

        UserRoleJpaMapper.toDomainModel(userRoleJpaEntity);
    }

    @Override
    @Transactional
    public Permission savePermission(Permission permission) {
        PermissionJpaEntity permissionJpaEntity = jpaPermissionRepository.save(
                PermissionJpaMapper.toJpaEntity(permission)
        );

        return PermissionJpaMapper.toDomainModel(permissionJpaEntity);
    }

    @Override
    @Transactional
    public void assignPermissionToRole(RolePermission rolePermission) {
        RolePermissionJpaEntity rolePermissionJpaEntity = jpaRolePermissionRepository.save(
                RolePermissionJpaMapper.toJpaEntity(rolePermission)
        );

        RolePermissionJpaMapper.toDomainModel(rolePermissionJpaEntity);
    }
}
