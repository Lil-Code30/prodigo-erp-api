package com.licode.prodigoerp.auth.controller;

import com.licode.prodigoerp.auth.dto.AuthResponse;
import com.licode.prodigoerp.auth.dto.LoginRequest;
import com.licode.prodigoerp.auth.dto.RefreshResponse;
import com.licode.prodigoerp.auth.dto.RegisterRequest;
import com.licode.prodigoerp.auth.service.AuthService;
import com.licode.prodigoerp.auth.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping(path = "/api/{version}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(value = "/register", version = "1.0")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authService.register(registerRequest);

        String refreshToken = authResponse.refreshToken();

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .path("/")
                .secure(true)
                .maxAge(Duration.ofDays(14))
                .sameSite("strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(authResponse);
    }

    @PostMapping(value = "/login", version = "1.0")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);

        String refreshToken = authResponse.refreshToken();

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .path("/")
                .secure(true)
                .maxAge(Duration.ofDays(14))
                .sameSite("strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(authResponse);
    }

    @GetMapping(value = "/refresh", version = "1.0")
    public ResponseEntity<RefreshResponse> refresh(@CookieValue("refresh_token") String refreshToken) {

        RefreshResponse  refreshResponse = authService.refreshAccessToken(refreshToken);

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .path("/")
                .secure(true)
                .maxAge(Duration.ofDays(14))
                .sameSite("strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(refreshResponse);

    }

    @GetMapping(value = "/logout", version = "1.0")
    public ResponseEntity<Void> logout(@CookieValue("refresh_token") String refreshToken) {

        refreshTokenService.revoke(refreshToken);

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", null)
                .httpOnly(true)
                .path("/")
                .secure(true)
                .maxAge(0)
                .sameSite("strict")
                .build();


        SecurityContextHolder.getContext().setAuthentication(null);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).build();

    }
}
