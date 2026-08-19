package com.licode.prodigoerp.module.adapter.output.persistence.module;

import com.licode.prodigoerp.module.application.port.output.ModuleQueryRepositoryPort;
import com.licode.prodigoerp.module.domain.model.Module;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ModuleQueryAdapter implements ModuleQueryRepositoryPort {

    private final JpaModuleRepository jpaModuleRepository;

    @Override
    public Optional<Module> findModuleByModuleKey(String moduleKey) {
        Optional<ModuleJpaEntity> moduleJpaEntity = jpaModuleRepository.findModuleByModuleKey(moduleKey);

        return moduleJpaEntity.map(ModuleJpaMapper::toDomainModel);
    }
}
