package com.licode.prodigoerp.module.service;

import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.common.security.SecurityUtils;
import com.licode.prodigoerp.common.security.dto.JwtPrincipal;
import com.licode.prodigoerp.module.dto.RegisterSelectedModule;
import com.licode.prodigoerp.common.exception.NotFoundException;
import com.licode.prodigoerp.module.dto.RegisterModule;
import com.licode.prodigoerp.module.entity.Module;
import com.licode.prodigoerp.module.entity.ModuleSubscription;
import com.licode.prodigoerp.module.mapper.ModuleMapper;
import com.licode.prodigoerp.module.repository.ModuleRepository;
import com.licode.prodigoerp.module.repository.ModuleSubscriptionRepository;
import com.licode.prodigoerp.tenant.entity.Tenant;
import com.licode.prodigoerp.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleSubscriptionRepository moduleSubscriptionRepository;
    private final TenantService  tenantService;

    public Module createModule(RegisterModule registerModule) {

        JwtPrincipal user = SecurityUtils.getCurrentUser();

        //  Here we check if there is a superAdmin connected or else we take the system default name
        String actor = user.username() == null ? SystemConstants.SYSTEM_NAME : user.username();

        Module newModule = ModuleMapper.toModuleEntity(registerModule, actor);

        // TODO: We need to generate the permissions for the module creted

        return moduleRepository.save(newModule);
    }

    public Module findModuleById(Long id) {

        Optional<Module> fetchedModule = moduleRepository.findById(id);

        if(fetchedModule.isEmpty()){
            throw new NotFoundException("Module Not Found with id: " + id);
        }

        return fetchedModule.get();
    }

    public void createTenantModuleSubscription(Long tenantId, List<RegisterSelectedModule> registerSelectedModules) {

        JwtPrincipal user = SecurityUtils.getCurrentUser();
        String actor = user.username() == null ? SystemConstants.SYSTEM_NAME : user.username();

        // first need to fetch all the Selected module Object
        // (Because, here the frontend juste provide the module id, name and key, but we need the whole Module object)
        List<Module> selectedModulesObj = registerSelectedModules.stream()
                .map(
                        rm -> {
                            Optional<Module> fetchedModule = moduleRepository.findModuleById(rm.moduleId());

                            if(fetchedModule.isEmpty()){
                                throw new NotFoundException("Module Not Found with id: " + rm.moduleId());
                            }

                            return fetchedModule.get();
                        }
                ).toList();

        // Fetch for the tenant infos with the tenant id
        Tenant tenant = tenantService.getTenantById(tenantId);

        // with the Tenant info and the fetched modules, we can now create a ModuleSubscription
        // for every module in the selectedModulesObj
        selectedModulesObj.forEach(selectedModule -> {

            // to determine which Subscription is free, we just check if the module is the first in the list
            boolean isFree = selectedModulesObj.getFirst() == selectedModule;
            BigDecimal price = isFree ? BigDecimal.valueOf(0) : selectedModule.getPrice();

            ModuleSubscription  moduleSubscription = ModuleMapper.toModuleSubscriptionEntity(
                    tenant,
                    selectedModule,
                    actor,
                    isFree,
                    price
            );

            moduleSubscriptionRepository.save(moduleSubscription);
        });


    }

}
