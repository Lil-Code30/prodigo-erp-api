package com.licode.prodigoerp.module.application.port.output;

import com.licode.prodigoerp.module.domain.model.ModuleSubscription;

public interface ModuleSubscriptionCreatePort {

    ModuleSubscription create(ModuleSubscription moduleSubscription);
}
