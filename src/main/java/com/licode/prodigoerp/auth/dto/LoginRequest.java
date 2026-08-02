package com.licode.prodigoerp.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
