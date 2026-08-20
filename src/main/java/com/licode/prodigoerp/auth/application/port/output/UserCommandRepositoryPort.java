package com.licode.prodigoerp.auth.application.port.output;

import com.licode.prodigoerp.auth.adapter.input.rest.dto.RegisterRequestDto;
import com.licode.prodigoerp.auth.domain.model.User;

public interface UserCommandRepositoryPort {

    User registerUser(RegisterRequestDto registerRequestDto);

}
