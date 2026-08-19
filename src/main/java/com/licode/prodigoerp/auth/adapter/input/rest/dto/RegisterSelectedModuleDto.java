package com.licode.prodigoerp.auth.adapter.input.rest.dto;


public record RegisterSelectedModuleDto(
        Long moduleId,
        String moduleName,
        String moduleKey
) {

}
