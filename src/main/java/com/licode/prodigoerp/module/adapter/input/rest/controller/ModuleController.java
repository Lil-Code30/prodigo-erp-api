package com.licode.prodigoerp.module.adapter.input.rest.controller;


import com.licode.prodigoerp.module.adapter.input.rest.dto.ShowPublicModuleDto;
import com.licode.prodigoerp.module.adapter.input.rest.mapper.ModuleWebMapper;
import com.licode.prodigoerp.module.application.port.input.AllPublicModuleLookUp;
import com.licode.prodigoerp.module.application.port.input.command.ShowPublicModuleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/{version}/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleWebMapper moduleWebMapper;
    private final AllPublicModuleLookUp allPublicModuleLookUp;

    @GetMapping(value = "/public", version = "1.0")
    public ResponseEntity<List<ShowPublicModuleDto>> publicModule() {

        List<ShowPublicModuleCommand> showPublicModuleCommandList = allPublicModuleLookUp.findAllPublicModules();

        return ResponseEntity.ok().body(
                showPublicModuleCommandList.stream().map(
                        moduleWebMapper::toShowPublicModuleDto
                ).toList()
        );
    }
}
