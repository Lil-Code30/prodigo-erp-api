package com.licode.prodigoerp.tenant.controller;

import com.licode.prodigoerp.tenant.entity.Tenant;
import com.licode.prodigoerp.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/tenant")
public class TenantController {

    private final TenantService tenantService;


    @GetMapping
    public ResponseEntity<List<Tenant>> getAllTenant() {

        List<Tenant> tenants = tenantService.getAllTenants();

        return ResponseEntity.ok().body(tenants);
    }
}
