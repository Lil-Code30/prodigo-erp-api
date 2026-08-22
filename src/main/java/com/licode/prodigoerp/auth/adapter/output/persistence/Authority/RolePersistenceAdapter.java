package com.licode.prodigoerp.auth.adapter.output.persistence.Authority;

import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity.RoleJpaEntity;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.mapper.RoleJpaMapper;
import com.licode.prodigoerp.auth.adapter.output.persistence.Authority.repository.JpaRoleRepository;
import com.licode.prodigoerp.auth.application.port.output.RoleQueryPort;
import com.licode.prodigoerp.auth.application.port.output.SaveRolePort;
import com.licode.prodigoerp.auth.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RoleQueryPort, SaveRolePort {
    final private JpaRoleRepository jpaRoleRepository;

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
}
