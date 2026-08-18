package com.licode.prodigoerp.auth.adapter.input.rest.dto;


public record RegisterSelectedModule(
        Long moduleId,
        String moduleName,
        String moduleKey
) {

}
