package com.licode.prodigoerp.auth.adapter.input.rest.mapper;

import com.licode.prodigoerp.auth.adapter.input.rest.dto.AuthResponseDto;
import com.licode.prodigoerp.auth.adapter.input.rest.dto.RefreshResponseDto;
import com.licode.prodigoerp.auth.adapter.input.rest.dto.RegisterRequestDto;
import com.licode.prodigoerp.auth.application.port.input.command.AuthResponseCommand;
import com.licode.prodigoerp.auth.application.port.input.command.RefreshResponseCommand;
import com.licode.prodigoerp.auth.application.port.input.command.RegisterUserCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthWebMapper {


    RegisterUserCommand toRegisterUserCommand(RegisterRequestDto registerRequestDto);
    AuthResponseDto toAuthResponseDto(AuthResponseCommand authResponseCommand);
    RefreshResponseDto toRefreshResponseDto(RefreshResponseCommand refreshResponseCommand);
}
