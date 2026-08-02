package com.licode.prodigoerp.auth.controller;

import com.licode.prodigoerp.auth.dto.AuthResponse;
import com.licode.prodigoerp.auth.dto.RegisterRequest;
import com.licode.prodigoerp.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.ok().body(authService.register(registerRequest));
    }
}
