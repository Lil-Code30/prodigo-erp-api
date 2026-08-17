package com.licode.prodigoerp.auth.adapter.input.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequestDto(@NotBlank String refreshToken) {
}
