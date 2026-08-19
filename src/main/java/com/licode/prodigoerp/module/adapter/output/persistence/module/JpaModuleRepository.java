package com.licode.prodigoerp.module.adapter.output.persistence.module;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaModuleRepository extends JpaRepository<ModuleJpaEntity, Long> {
    Optional<ModuleJpaEntity> findModuleByModuleKey(String moduleKey);
    Optional<ModuleJpaEntity> findModuleById(Long moduleId);

}
