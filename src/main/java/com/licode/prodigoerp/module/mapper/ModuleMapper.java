package com.licode.prodigoerp.module.mapper;

import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.module.dto.RegisterModule;
import com.licode.prodigoerp.module.entity.Module;
import com.licode.prodigoerp.module.entity.ModuleSubscription;
import com.licode.prodigoerp.tenant.entity.Tenant;

import java.math.BigDecimal;
import java.time.Instant;

public class ModuleMapper {

    public static Module toModuleEntity(RegisterModule registerModule, String actor) {

        Module module = new Module();
        module.setName(registerModule.name());
        module.setModuleKey(registerModule.moduleKey());
        module.setPrice(registerModule.price());
        module.setCurrency(SystemConstants.SYSTEM_CURRENCY);
        module.setIsActive(true);

        Instant now = Instant.now();

        module.setCreatedAt(now);
        module.setUpdatedAt(now);
        module.setCreatedBy(actor);
        module.setUpdatedBy(actor);


        return module;
    }

    public static ModuleSubscription toModuleSubscriptionEntity(Tenant tenant, Module module, String actor, Boolean isFree, BigDecimal price) {
        ModuleSubscription moduleSubscription = new ModuleSubscription();

        moduleSubscription.setTenant(tenant);
        moduleSubscription.setModule(module);
        moduleSubscription.setIsFree(isFree);
        moduleSubscription.setPrice(price);
        moduleSubscription.setStatus("ACTIVE");
        moduleSubscription.setCurrency(SystemConstants.SYSTEM_CURRENCY);

        Instant now = Instant.now();
        moduleSubscription.setActivatedAt(now);
        moduleSubscription.setExpiresAt(now.plusSeconds(2592000)); // NOTE: consider the free subscription that never expires

        moduleSubscription.setCreatedAt(now);
        moduleSubscription.setUpdatedAt(now);
        moduleSubscription.setCreatedBy(actor);
        moduleSubscription.setUpdatedBy(actor);

        return moduleSubscription;
    }
}
