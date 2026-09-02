package com.licode.prodigoerp.auth.application.port.input;


import com.licode.prodigoerp.auth.application.port.input.command.RegisterSuperAdminCommand;

public interface RegisterSuperAdminUseCase {

    String register(RegisterSuperAdminCommand registerSuperAdminCommand);
}
