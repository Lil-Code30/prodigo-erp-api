package com.licode.prodigoerp.auth.application.port.input;

import com.licode.prodigoerp.auth.application.port.input.command.CreateUserCommand;
import com.licode.prodigoerp.auth.domain.model.User;
import com.licode.prodigoerp.tenant.domain.model.Tenant;

public interface SaveUserUseCase {

    User save(CreateUserCommand command, String author);
}
