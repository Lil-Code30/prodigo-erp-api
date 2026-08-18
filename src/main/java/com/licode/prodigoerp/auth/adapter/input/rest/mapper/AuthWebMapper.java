package com.licode.prodigoerp.auth.adapter.input.rest.mapper;

import com.licode.prodigoerp.auth.adapter.input.rest.dto.RegisterRequestDto;
import com.licode.prodigoerp.auth.adapter.input.rest.dto.RegisterSelectedModule;
import com.licode.prodigoerp.auth.domain.command.RegisterUserCommand;
import com.licode.prodigoerp.auth.domain.command.SelectedModuleCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthWebMapper {


    RegisterUserCommand toRegisterUserCommand(RegisterRequestDto registerRequestDto);
    SelectedModuleCommand toSelectedModuleCommand(RegisterSelectedModule registerSelectedModule);
}
