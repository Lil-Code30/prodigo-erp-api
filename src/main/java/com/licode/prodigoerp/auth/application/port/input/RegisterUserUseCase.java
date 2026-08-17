package com.licode.prodigoerp.auth.application.port.input;

import com.licode.prodigoerp.auth.adapter.input.rest.dto.AuthResponseDto;
import com.licode.prodigoerp.auth.adapter.input.rest.dto.RegisterRequestDto;

public interface RegisterUserUseCase {

    AuthResponseDto register(RegisterRequestDto registerRequestDto);

}
