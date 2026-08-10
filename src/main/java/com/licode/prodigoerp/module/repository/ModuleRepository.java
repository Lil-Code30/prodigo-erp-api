package com.licode.prodigoerp.module.repository;

import com.licode.prodigoerp.module.entity.Module;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    Optional<Module> findModuleByModuleKey(String moduleKey);
    Optional<Module> findModuleById(Long moduleId);
}
