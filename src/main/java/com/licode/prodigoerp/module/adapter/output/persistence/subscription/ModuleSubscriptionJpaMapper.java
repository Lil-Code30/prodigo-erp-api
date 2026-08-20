package com.licode.prodigoerp.module.adapter.output.persistence.subscription;

import com.licode.prodigoerp.module.adapter.output.persistence.module.ModuleJpaMapper;
import com.licode.prodigoerp.module.domain.model.ModuleSubscription;
import com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant.TenantJpaMapper;

public class ModuleSubscriptionJpaMapper {

    public static ModuleSubscription toDomainModel(ModuleSubscriptionJpaEntity moduleSubscriptionJpaEntity) {
        ModuleSubscription moduleSubscription = new ModuleSubscription();

        moduleSubscription.setId(moduleSubscriptionJpaEntity.getId());
        moduleSubscription.setTenant(TenantJpaMapper.toDomainModel(moduleSubscriptionJpaEntity.getTenantJpaEntity()));
        moduleSubscription.setModule(ModuleJpaMapper.toDomainModel(moduleSubscriptionJpaEntity.getModuleJpaEntity()));
        moduleSubscription.setStatus(moduleSubscriptionJpaEntity.getStatus());
        moduleSubscription.setIsFree(moduleSubscriptionJpaEntity.getIsFree());
        moduleSubscription.setPrice(moduleSubscriptionJpaEntity.getPrice());
        moduleSubscription.setCurrency(moduleSubscriptionJpaEntity.getCurrency());
        moduleSubscription.setActivatedAt(moduleSubscriptionJpaEntity.getActivatedAt());
        moduleSubscription.setExpiresAt(moduleSubscriptionJpaEntity.getExpiresAt());
        moduleSubscription.setCreatedAt(moduleSubscriptionJpaEntity.getCreatedAt());
        moduleSubscription.setUpdatedAt(moduleSubscriptionJpaEntity.getUpdatedAt());
        moduleSubscription.setUpdatedBy(moduleSubscriptionJpaEntity.getUpdatedBy());
        moduleSubscription.setCreatedBy(moduleSubscriptionJpaEntity.getCreatedBy());


        return moduleSubscription;
    }

    public static ModuleSubscriptionJpaEntity toJpaEntity(ModuleSubscription moduleSubscription) {
        ModuleSubscriptionJpaEntity moduleSubscriptionJpaEntity = new ModuleSubscriptionJpaEntity();

        moduleSubscriptionJpaEntity.setId(moduleSubscription.getId());
        moduleSubscriptionJpaEntity.setTenantJpaEntity(TenantJpaMapper.toJpaEntity(moduleSubscription.getTenant()));
        moduleSubscriptionJpaEntity.setModuleJpaEntity(ModuleJpaMapper.toJpaEntity(moduleSubscription.getModule()));
        moduleSubscriptionJpaEntity.setStatus(moduleSubscription.getStatus());
        moduleSubscriptionJpaEntity.setIsFree(moduleSubscription.getIsFree());
        moduleSubscriptionJpaEntity.setPrice(moduleSubscription.getPrice());
        moduleSubscriptionJpaEntity.setCurrency(moduleSubscription.getCurrency());
        moduleSubscriptionJpaEntity.setActivatedAt(moduleSubscription.getActivatedAt());
        moduleSubscriptionJpaEntity.setExpiresAt(moduleSubscription.getExpiresAt());
        moduleSubscriptionJpaEntity.setCreatedAt(moduleSubscription.getCreatedAt());
        moduleSubscriptionJpaEntity.setUpdatedAt(moduleSubscription.getUpdatedAt());
        moduleSubscriptionJpaEntity.setUpdatedBy(moduleSubscription.getUpdatedBy());
        moduleSubscriptionJpaEntity.setCreatedBy(moduleSubscription.getCreatedBy());


        return moduleSubscriptionJpaEntity;
    }
}
