package com.licode.prodigoerp.auth.domain.exception.dto;

public record FieldErrorResponse(
        String field,
        String message
) {
}