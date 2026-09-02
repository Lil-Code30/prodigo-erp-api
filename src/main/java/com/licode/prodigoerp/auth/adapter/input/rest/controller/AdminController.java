package com.licode.prodigoerp.auth.adapter.input.rest.controller;

import com.licode.prodigoerp.auth.adapter.input.rest.dto.CreateSuperAdminDto;
import com.licode.prodigoerp.auth.adapter.input.rest.mapper.AuthWebMapper;
import com.licode.prodigoerp.auth.application.port.input.RegisterSuperAdminUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/{version}/s/admin")
@RequiredArgsConstructor
public class AdminController {

    private final RegisterSuperAdminUseCase registerSuperAdminUseCase;
    private final AuthWebMapper authWebMapper;

    @PostMapping(value = "/create", version = "1.0")
    public ResponseEntity<String> createSuperAdmin(@Valid @RequestBody CreateSuperAdminDto createSuperAdminDto) {
        return ResponseEntity.ok().body(
                registerSuperAdminUseCase.register(authWebMapper.toRegisterSuperAdminCommand(createSuperAdminDto))
        );
    }
}
