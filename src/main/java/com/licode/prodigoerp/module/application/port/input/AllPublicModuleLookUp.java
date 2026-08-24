package com.licode.prodigoerp.module.application.port.input;

import com.licode.prodigoerp.module.application.port.input.command.ShowPublicModuleCommand;

import java.util.List;

public interface AllPublicModuleLookUp {

    List<ShowPublicModuleCommand> findAllPublicModules();
}
