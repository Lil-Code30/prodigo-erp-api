package com.licode.prodigoerp.tenant.controller;

import com.licode.prodigoerp.tenant.entity.Tenant;
import com.licode.prodigoerp.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{version}/admin/tenants")
public class TenantController {

    private final TenantService tenantService;


//    TODO: consider using pagination
    @GetMapping( version = "1.0")
    public ResponseEntity<List<Tenant>> findAllTenants() {
        return ResponseEntity.ok().body(tenantService.getAllTenants());
    }

    @GetMapping(path = "/{slug}", version = "1.0")
    public ResponseEntity<Tenant> findTenantById(@PathVariable String slug) {
        return ResponseEntity.ok().body(tenantService.getTenantBySlug(slug));
    }

//    NOTE: Need to figure out if there wil be and endpoint to create a Tenant on
//    itself without passing through the register endpoint
//    @PostMapping(path = "/create", version = "1.0")
//    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {
//
//    }

    @PatchMapping(path = "/{slug}/status", version = "1.0")
    public ResponseEntity<Void> changeTenantStatus(@PathVariable String slug, @RequestBody String status) {

        tenantService.changeTenantStatus(slug, status);

        return ResponseEntity.noContent().build();
    }
}
