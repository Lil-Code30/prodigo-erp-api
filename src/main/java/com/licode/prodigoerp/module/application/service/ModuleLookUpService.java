package com.licode.prodigoerp.module.application.service;

import com.licode.prodigoerp.module.application.port.input.AllPublicModuleLookUp;
import com.licode.prodigoerp.module.application.port.input.ModuleLookUpUseCase;
import com.licode.prodigoerp.module.application.port.input.command.ShowPublicModuleCommand;
import com.licode.prodigoerp.module.application.port.output.ModuleQueryPort;
import com.licode.prodigoerp.module.domain.model.Module;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleLookUpService implements ModuleLookUpUseCase, AllPublicModuleLookUp {

    private final ModuleQueryPort moduleQueryPort;

    @Override
    public Optional<Module> findModuleByModuleKey(String moduleKey) {
        return moduleQueryPort.findModuleByModuleKey(moduleKey);
    }


    @Override
    public List<ShowPublicModuleCommand> findAllPublicModules() {

        List<Module> moduleList = moduleQueryPort.findAllActivePublicModules();

        List<ShowPublicModuleCommand> showPublicModuleCommandList = new ArrayList<>();

        moduleList.forEach(module -> {

            showPublicModuleCommandList.add(new ShowPublicModuleCommand(
                    module.getId(),
                    module.getName(),
                    module.getDescription(),
                    module.getModuleKey(),
                    module.getPrice(),
                    module.getCurrency()
            ));
        });

        return showPublicModuleCommandList;
    }
}
