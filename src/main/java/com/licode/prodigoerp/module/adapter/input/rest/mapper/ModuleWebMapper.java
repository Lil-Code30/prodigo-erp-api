package com.licode.prodigoerp.module.adapter.input.rest.mapper;

import com.licode.prodigoerp.module.adapter.input.rest.dto.ShowPublicModuleDto;
import com.licode.prodigoerp.module.application.port.input.command.ShowPublicModuleCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModuleWebMapper {

    ShowPublicModuleDto toShowPublicModuleDto(ShowPublicModuleCommand showPublicModuleCommand);
}
