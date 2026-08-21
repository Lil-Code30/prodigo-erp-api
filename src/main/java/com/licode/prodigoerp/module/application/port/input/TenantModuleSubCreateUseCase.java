package com.licode.prodigoerp.module.application.port.input;

import com.licode.prodigoerp.module.application.port.input.command.SelectedModuleCommand;
import com.licode.prodigoerp.module.domain.model.Module;

import java.util.List;
import java.util.Map;

public interface TenantModuleSubCreateUseCase {

    Map<String, Module> createTenantModuleSubscription(Long tenantId, List<SelectedModuleCommand> selectedModuleCommands);
}
