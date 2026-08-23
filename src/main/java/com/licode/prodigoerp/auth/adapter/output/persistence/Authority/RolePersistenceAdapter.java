package com.licode.prodigoerp.auth.adapter.output.persistence.Authority;

import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.RoleJpaEntity;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.UserRoleJpaEntity;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.mapper.RoleJpaMapper;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.mapper.UserRoleJpaMapper;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.repository.JpaRoleRepository;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.repository.JpaUserRoleRepository;
import com.licode.prodigoerp.auth.application.port.output.RoleQueryPort;
import com.licode.prodigoerp.auth.application.port.output.SaveRolePort;
import com.licode.prodigoerp.auth.domain.model.Role;
import com.licode.prodigoerp.auth.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RoleQueryPort, SaveRolePort {
    final private JpaRoleRepository jpaRoleRepository;
    final private JpaUserRoleRepository jpaUserRoleRepository;

    @Override
    public List<String> findActiveRoleNames(Long userId) {

        return List.of();
    }

    @Override
    public List<String> findActivePermissionCodes(Long userId) {

        // TODO : TO BE done
        return List.of();
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
}
