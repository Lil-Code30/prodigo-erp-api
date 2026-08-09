package com.licode.prodigoerp.common.exception.dto;

public record FieldErrorResponse(
        String field,
        String message
) {
}