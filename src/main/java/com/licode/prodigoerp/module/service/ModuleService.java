package com.licode.prodigoerp.module.service;

import com.licode.prodigoerp.common.exception.ConflictException;
import com.licode.prodigoerp.common.exception.JwtValidationException;
import com.licode.prodigoerp.common.security.SecurityUtils;
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
import com.licode.prodigoerp.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleSubscriptionRepository moduleSubscriptionRepository;
    private final TenantService  tenantService;
    private final PermissionService permissionService;

    @Transactional
    public Module createModule(RegisterModule registerModule) {

        // check if there is already a Module with the moduleKey provided
        if(moduleRepository.findModuleByModuleKey(registerModule.moduleKey().toUpperCase()).isPresent()) {
            throw new ConflictException("Module with this key: " + registerModule.moduleKey() + " already exists");
        }

        //  Here we check if there is a superAdmin connected or else we take the system default name
        String actor = SecurityUtils.getCurrentUsernameOrElseSysName();

        Module newModule = ModuleMapper.toModuleEntity(registerModule, actor);

        Module createdModule = moduleRepository.save(newModule);

        // We need to generate all permissions for the module created
        registerModule.createPermissions()
                .forEach( permission -> {
                    permissionService.createPermission(permission, createdModule.getModuleKey());
                });

        return createdModule;
    }

    public List<Module> findAllModules() {
        return moduleRepository.findAll();
    }

    public Module findModuleById(Long id) {

        Optional<Module> fetchedModule = moduleRepository.findById(id);

        if(fetchedModule.isEmpty()){
            throw new NotFoundException("Module Not Found with id: " + id);
        }

        return fetchedModule.get();
    }

    public Module findModuleByModuleKey(String moduleKey) {
        Optional<Module> fetchedModule = moduleRepository.findModuleByModuleKey(moduleKey.toUpperCase());

        if(fetchedModule.isEmpty()){
            throw new NotFoundException("Module Not Found with id: " + moduleKey);
        }

        return fetchedModule.get();
    }

    @Transactional
    public void changeModuleStatus(String moduleKey) {
        Optional<Module> fetchedModule = moduleRepository.findModuleByModuleKey(moduleKey.toUpperCase());

        if(fetchedModule.isEmpty()){
            throw new NotFoundException("Module Not Found with id: " + moduleKey);
        }

        Module newStatusModule = fetchedModule.get();

        // if newStatusModule.getIsActive() == true  then set the status to false else set it to true :
        Boolean newStatus = !newStatusModule.getIsActive();
        newStatusModule.setIsActive(newStatus);
        newStatusModule.setUpdatedAt(Instant.now());
        String actor = "";

        try{
            actor = SecurityUtils.getCurrentUser().username();
        }catch(Exception e){
            throw new JwtValidationException("Invalid username");
        }

        newStatusModule.setUpdatedBy(actor);

        moduleRepository.save(newStatusModule);
    }

    @Transactional
    public Map<String, Module> createTenantModuleSubscription(Long tenantId, List<RegisterSelectedModule> registerSelectedModules) {

        String actor = SecurityUtils.getCurrentUsernameOrElseSysName();

        // to keep track of all the modules subscribe by the tenant
        Map<String, Module> allModuleSubscriptions = new HashMap<>();

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

            allModuleSubscriptions.put(selectedModule.getModuleKey(), selectedModule);

            moduleSubscriptionRepository.save(moduleSubscription);
        });

        return allModuleSubscriptions;
    }

}
