package com.licode.prodigoerp.module.application.port.input;

import com.licode.prodigoerp.module.domain.model.Module;

import java.util.Optional;

public interface ModuleLookUpUseCase {

    Optional<Module> findModuleByModuleKey(String moduleKey);
}
