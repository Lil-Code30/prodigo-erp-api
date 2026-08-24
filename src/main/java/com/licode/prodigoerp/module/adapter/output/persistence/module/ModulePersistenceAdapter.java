package com.licode.prodigoerp.module.adapter.output.persistence.module;

import com.licode.prodigoerp.module.application.port.output.ModuleQueryPort;
import com.licode.prodigoerp.module.application.port.output.SaveModulePort;
import com.licode.prodigoerp.module.domain.model.Module;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ModulePersistenceAdapter implements ModuleQueryPort, SaveModulePort {

    private final JpaModuleRepository jpaModuleRepository;

    @Override
    public Optional<Module> findModuleByModuleKey(String moduleKey) {
        Optional<ModuleJpaEntity> moduleJpaEntity = jpaModuleRepository.findModuleByModuleKey(moduleKey);

        return moduleJpaEntity.map(ModuleJpaMapper::toDomainModel);
    }

    @Override
    public List<Module> findAllActivePublicModules() {
        List<ModuleJpaEntity> moduleJpaEntityList = jpaModuleRepository.findAllActiveModuleJpaEntities();

        return moduleJpaEntityList.stream().map(ModuleJpaMapper::toDomainModel).toList();
    }

    @Override
    @Transactional
    public Module saveModule(Module module) {
        ModuleJpaEntity moduleJpaEntity = jpaModuleRepository.save(ModuleJpaMapper.toJpaEntity(module));

        return ModuleJpaMapper.toDomainModel(moduleJpaEntity);
    }
}
