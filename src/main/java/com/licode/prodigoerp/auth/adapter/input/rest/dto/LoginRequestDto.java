package com.licode.prodigoerp.auth.adapter.input.rest.dto;

import jakarta.validation.constraints.NotBlank;


public record LoginRequestDto(
        @NotBlank String username,
        @NotBlank String password
) {
}
