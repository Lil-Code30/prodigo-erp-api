package com.licode.prodigoerp.auth.adapter.input.rest.mapper;

import com.licode.prodigoerp.auth.adapter.input.rest.dto.RegisterRequestDto;
import com.licode.prodigoerp.auth.adapter.input.rest.dto.RegisterSelectedModuleDto;
import com.licode.prodigoerp.auth.application.port.input.command.RegisterUserCommand;
import com.licode.prodigoerp.module.domain.command.SelectedModuleCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthWebMapper {


    RegisterUserCommand toRegisterUserCommand(RegisterRequestDto registerRequestDto);
    SelectedModuleCommand toSelectedModuleCommand(RegisterSelectedModuleDto registerSelectedModuleDto);
}
