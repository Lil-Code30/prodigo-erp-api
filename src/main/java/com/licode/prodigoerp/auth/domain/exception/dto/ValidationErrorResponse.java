package com.licode.prodigoerp.auth.domain.exception.dto;

import java.time.Instant;
import java.util.List;

public record ValidationErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        String errorCode,
        String traceId,
        List<FieldErrorResponse> errors
) {
}
