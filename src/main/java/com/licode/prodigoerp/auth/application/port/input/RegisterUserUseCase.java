package com.licode.prodigoerp.auth.application.port.input;

import com.licode.prodigoerp.auth.domain.command.AuthResponseCommand;
import com.licode.prodigoerp.auth.domain.command.RegisterUserCommand;

public interface RegisterUserUseCase {

    AuthResponseCommand register(RegisterUserCommand registerUserCommand);

}
