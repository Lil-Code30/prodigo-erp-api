package com.licode.prodigoerp.module.adapter.output.persistence.module;

import com.licode.prodigoerp.module.domain.model.Module;

public class ModuleJpaMapper {

    public static Module toDomainModel(ModuleJpaEntity moduleJpaEntity) {
        Module module = new Module();

        module.setId(moduleJpaEntity.getId());
        module.setName(moduleJpaEntity.getName());
        module.setPrice(moduleJpaEntity.getPrice());
        module.setCurrency(moduleJpaEntity.getCurrency());
        module.setIsActive(moduleJpaEntity.getIsActive());
        module.setCreatedAt(moduleJpaEntity.getCreatedAt());
        module.setUpdatedAt(moduleJpaEntity.getUpdatedAt());
        module.setCreatedBy(moduleJpaEntity.getCreatedBy());
        module.setUpdatedBy(moduleJpaEntity.getUpdatedBy());

        return module;
    }

    public static ModuleJpaEntity toJpaEntity(Module module) {

        ModuleJpaEntity moduleJpaEntity = new ModuleJpaEntity();

        moduleJpaEntity.setId(module.getId());
        moduleJpaEntity.setName(module.getName());
        moduleJpaEntity.setPrice(module.getPrice());
        moduleJpaEntity.setCurrency(module.getCurrency());
        moduleJpaEntity.setIsActive(module.getIsActive());
        moduleJpaEntity.setCreatedAt(module.getCreatedAt());
        moduleJpaEntity.setUpdatedAt(module.getUpdatedAt());
        moduleJpaEntity.setCreatedBy(module.getCreatedBy());
        moduleJpaEntity.setUpdatedBy(module.getUpdatedBy());

        return moduleJpaEntity;
    }
}
