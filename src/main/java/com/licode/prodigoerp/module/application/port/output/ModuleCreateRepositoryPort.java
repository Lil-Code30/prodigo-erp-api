package com.licode.prodigoerp.module.application.port.output;

import com.licode.prodigoerp.module.application.port.input.command.RegisterModuleCommand;
import com.licode.prodigoerp.module.domain.model.Module;

public interface ModuleCreateRepositoryPort {

    Module createModule(RegisterModuleCommand registerModuleCommand);
}
