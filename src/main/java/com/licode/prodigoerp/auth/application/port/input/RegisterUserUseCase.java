package com.licode.prodigoerp.auth.application.port.input;

import com.licode.prodigoerp.auth.application.port.input.command.AuthResponseCommand;
import com.licode.prodigoerp.auth.application.port.input.command.RegisterUserCommand;

public interface RegisterUserUseCase {

    AuthResponseCommand register(RegisterUserCommand registerUserCommand);

}
