package com.licode.prodigoerp.auth.adapter.input.rest.dto;


import java.util.UUID;

public record RegisterSelectedModuleDto(
        UUID moduleId,
        String moduleName,
        String moduleKey
) {

}
