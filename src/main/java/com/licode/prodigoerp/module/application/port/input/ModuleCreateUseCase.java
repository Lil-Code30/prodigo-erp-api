package com.licode.prodigoerp.module.application.port.input;

import com.licode.prodigoerp.module.application.port.input.command.RegisterModuleCommand;
import com.licode.prodigoerp.module.domain.model.Module;

public interface ModuleCreateUseCase {

    Module createModule(RegisterModuleCommand registerModuleCommand);
}
