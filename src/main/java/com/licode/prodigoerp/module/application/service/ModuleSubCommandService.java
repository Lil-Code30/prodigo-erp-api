package com.licode.prodigoerp.module.application.service;

import com.licode.prodigoerp.module.application.port.input.ModuleSubscriptionUseCase;
import com.licode.prodigoerp.module.application.port.output.ModuleSubscriptionCreatePort;
import com.licode.prodigoerp.module.domain.command.CreateModuleSubCommand;
import com.licode.prodigoerp.module.domain.model.ModuleSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ModuleSubCommandService implements ModuleSubscriptionUseCase {

    private final ModuleSubscriptionCreatePort moduleSubscriptionCreatePort;

    @Override
    public ModuleSubscription createModuleSubscription(CreateModuleSubCommand createModuleSubCommand) {

        // TODO : need to fetch the person connected or take the system name
        String actor = "PRODIGO_ERP_API";

        Instant instant = Instant.now();

        ModuleSubscription moduleSubscription = new ModuleSubscription();
        moduleSubscription.setId(null);
        moduleSubscription.setModule(createModuleSubCommand.module());
        moduleSubscription.setTenant(createModuleSubCommand.tenant());
        moduleSubscription.setStatus("ACTIVE");
        moduleSubscription.setIsFree(createModuleSubCommand.isFree());

        // TODO: Currency and pric need to depend on the Country of the user
        moduleSubscription.setPrice(createModuleSubCommand.price());
        moduleSubscription.setCurrency(createModuleSubCommand.currency());

        moduleSubscription.setActivatedAt(instant);
        moduleSubscription.setExpiresAt(Instant.now().plusSeconds(2592000)); // TODO : Free Plan never expires

        moduleSubscription.setCreatedAt(instant);
        moduleSubscription.setUpdatedAt(instant);
        moduleSubscription.setCreatedBy(actor);
        moduleSubscription.setUpdatedBy(actor);



        return moduleSubscriptionCreatePort.create(moduleSubscription);
    }
}
