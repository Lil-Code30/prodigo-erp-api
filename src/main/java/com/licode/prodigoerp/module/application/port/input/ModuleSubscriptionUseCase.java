package com.licode.prodigoerp.module.application.port.input;

import com.licode.prodigoerp.module.application.port.input.command.CreateModuleSubCommand;
import com.licode.prodigoerp.module.domain.model.ModuleSubscription;

public interface ModuleSubscriptionUseCase {

    ModuleSubscription createModuleSubscription(CreateModuleSubCommand createModuleSubCommand);
}
