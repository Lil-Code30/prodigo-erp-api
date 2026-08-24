package com.licode.prodigoerp.module.adapter.input.rest.controller;

import com.licode.prodigoerp.module.adapter.input.rest.dto.RegisterModuleDto;
import com.licode.prodigoerp.module.adapter.input.rest.mapper.ModuleWebMapper;
import com.licode.prodigoerp.module.application.port.input.ModuleCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{version}/s/admin/modules")
@RequiredArgsConstructor
public class AdminModuleController {
    private final ModuleCreateUseCase moduleCreateUseCase;
    private final ModuleWebMapper moduleWebMapper;

    @PostMapping(path = "/", version = "1.0")
    public ResponseEntity<String> createModule(@RequestBody RegisterModuleDto registerModuleDto) {

        moduleCreateUseCase.createModule(
                moduleWebMapper.toRegisterModuleCommand(registerModuleDto)
        );

        return ResponseEntity.ok("Module created successfully");
    }
}
