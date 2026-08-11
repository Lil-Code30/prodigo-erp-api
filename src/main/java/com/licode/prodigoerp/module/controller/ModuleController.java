package com.licode.prodigoerp.module.controller;

import com.licode.prodigoerp.module.dto.RegisterModule;
import com.licode.prodigoerp.module.entity.Module;
import com.licode.prodigoerp.module.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAuthority('PERM_ERP_CRUD')")
@RequestMapping("/api/{version}/s/admin/module")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping(path ="/", version = "1.0")
    public ResponseEntity<Module> save(@RequestBody RegisterModule registerModule) {

        Module module = moduleService.createModule(registerModule);

        return ResponseEntity.ok(module);
    }
}
