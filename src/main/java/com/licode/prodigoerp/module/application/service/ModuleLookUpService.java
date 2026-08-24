package com.licode.prodigoerp.module.application.service;

import com.licode.prodigoerp.module.application.port.input.ModuleLookUpUseCase;
import com.licode.prodigoerp.module.application.port.output.ModuleQueryPort;
import com.licode.prodigoerp.module.domain.model.Module;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleLookUpService implements ModuleLookUpUseCase {

    private final ModuleQueryPort moduleQueryPort;

    @Override
    public Optional<Module> findModuleByModuleKey(String moduleKey) {
        return moduleQueryPort.findModuleByModuleKey(moduleKey);
    }
}
