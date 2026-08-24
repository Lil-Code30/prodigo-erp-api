package com.licode.prodigoerp.module.application.port.output;

import com.licode.prodigoerp.module.domain.model.Module;

import java.util.List;
import java.util.Optional;

public interface ModuleQueryPort {

    Optional<Module> findModuleByModuleKey(String moduleKey);
    List<Module> findAllActivePublicModules();
}
