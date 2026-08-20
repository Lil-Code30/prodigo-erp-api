package com.licode.prodigoerp.module.application.service;

import com.licode.prodigoerp.auth.domain.exception.NotFoundException;
import com.licode.prodigoerp.module.application.port.input.ModuleLookUpUseCase;
import com.licode.prodigoerp.module.application.port.input.ModuleSubscriptionUseCase;
import com.licode.prodigoerp.module.application.port.input.TenantModuleSubCreateUseCase;
import com.licode.prodigoerp.module.domain.command.CreateModuleSubCommand;
import com.licode.prodigoerp.module.domain.command.SelectedModuleCommand;
import com.licode.prodigoerp.module.domain.model.Module;
import com.licode.prodigoerp.tenant.application.port.input.TenantLookUpUseCase;
import com.licode.prodigoerp.tenant.domain.model.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleService implements TenantModuleSubCreateUseCase {

    private final ModuleLookUpUseCase moduleLookUpUseCase;
    private final TenantLookUpUseCase tenantLookUpUseCase;
    private final ModuleSubscriptionUseCase moduleSubscriptionUseCase;

    @Override
    public Map<String, Module> createTenantModuleSubscription(Long tenantId, List<SelectedModuleCommand> selectedModuleCommands) {

        // TODO : need to fetch the person connected
        String actor = "PRODIGO_ERP_API";

        // to keep track of all the modules subscribe by the tenant
        Map<String, Module> allModuleSubscriptions = new HashMap<>();

        // first need to fetch all the Selected module Object
        // (Because, here the frontend juste provide the module id,
        // name and key, but we need the whole Module object)
        List<Module> selectedModuleObj = selectedModuleCommands.stream()
                .map(
                        selectedModuleCommand -> {
                            Optional<Module> fetchedModule = moduleLookUpUseCase.findModuleByModuleKey(selectedModuleCommand.moduleKey());

                            if(fetchedModule.isEmpty()){
                                throw new NotFoundException("Module Not Found with id: " + selectedModuleCommand.moduleKey());
                            }

                            return fetchedModule.get();
                        }
                ).toList();

        // Fetch for the tenant infos with the tenant id
        Tenant tenant = tenantLookUpUseCase.findTenantById(tenantId);

        // with the Tenant info and the fetched modules, we can now create a ModuleSubscription
        // for every module in the selectedModulesObj
        selectedModuleObj.forEach(selectedModule -> {

            // to determine which Subscription is free,
            // we just check if the module is the first in the list
            boolean isFree = selectedModuleObj.getFirst() == selectedModule;
            BigDecimal price = isFree ? BigDecimal.valueOf(0) : selectedModule.getPrice();


            allModuleSubscriptions.put(selectedModule.getModuleKey(), selectedModule);

            // then we need to save the moduleSubscription
            // TODO: Figure out to relate the currency depending on the user (Tenant) country
            CreateModuleSubCommand createModuleSubCommand = new CreateModuleSubCommand(
                    tenant,
                    selectedModule,
                    isFree,
                    price,
                    "XAF"
            );

            // Then Save the ModuleSub
            moduleSubscriptionUseCase.createModuleSubscription(createModuleSubCommand);
        });

        return allModuleSubscriptions;
    }
}
