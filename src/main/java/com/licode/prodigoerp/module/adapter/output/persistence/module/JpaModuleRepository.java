package com.licode.prodigoerp.module.adapter.output.persistence.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaModuleRepository extends JpaRepository<ModuleJpaEntity, UUID> {
    Optional<ModuleJpaEntity> findModuleByModuleKey(String moduleKey);
    Optional<ModuleJpaEntity> findModuleById(UUID moduleId);

    @Query("SELECT m FROM ModuleJpaEntity m WHERE m.isActive = true")
    List<ModuleJpaEntity> findAllActiveModuleJpaEntities();

}
