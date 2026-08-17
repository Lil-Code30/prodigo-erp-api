package com.licode.prodigoerp.auth.application.service;

import com.licode.prodigoerp.auth.application.port.input.RegisterUserUseCase;
import com.licode.prodigoerp.auth.adapter.input.rest.dto.AuthResponseDto;
import com.licode.prodigoerp.auth.adapter.input.rest.dto.RegisterRequestDto;

public class UserService implements RegisterUserUseCase {

    @Override
    public AuthResponseDto register(RegisterRequestDto registerRequestDto) {
        return null;
    }
}
