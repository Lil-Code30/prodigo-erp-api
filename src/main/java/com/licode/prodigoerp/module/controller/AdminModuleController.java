package com.licode.prodigoerp.module.controller;

import com.licode.prodigoerp.module.dto.RegisterModule;
import com.licode.prodigoerp.module.entity.Module;
import com.licode.prodigoerp.module.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('PERM_ERP_CRUD')")
@RequestMapping("/api/{version}/s/admin/modules")
@RequiredArgsConstructor
public class AdminModuleController {

    private final ModuleService moduleService;

    @PostMapping(path ="/", version = "1.0")
    public ResponseEntity<Module> save(@RequestBody RegisterModule registerModule) {

        Module module = moduleService.createModule(registerModule);

        return ResponseEntity.ok(module);
    }

    @GetMapping(path = "/", version = "1.0")
    public ResponseEntity<List<Module>> getAllModules() {
        return ResponseEntity.ok().body(moduleService.findAllModules());
    }

    @GetMapping(path = "/{moduleKey}" , version = "1.0")
    public ResponseEntity<Module> getModuleByKey(@PathVariable String moduleKey) {

        return ResponseEntity.ok().body(moduleService.findModuleByModuleKey(moduleKey));
    }

    @PostMapping(path = "/{moduleKey}/status")
    public ResponseEntity<String> changeModuleStatus(@PathVariable String moduleKey) {
        moduleService.changeModuleStatus(moduleKey);

        return ResponseEntity.ok().body("Module Status Changed");
    }
}
