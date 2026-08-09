package com.licode.prodigoerp.user.controller;


import com.licode.prodigoerp.user.dto.RegisterAdminRequest;
import com.licode.prodigoerp.user.entity.User;
import com.licode.prodigoerp.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/{version}/s/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping(path = "/admins", version = "1.0")
    public ResponseEntity<List<User>> getAllAdmins(){
        return ResponseEntity.ok().body(adminService.getAllSuperAdmin(true));
    }

    @GetMapping(path = "/admins/{adminId}", version = "1.0")
    public ResponseEntity<User> getAdminById(@PathVariable Long adminId){

        return ResponseEntity.ok().body(adminService.getSuperAdminById(adminId));
    }

    @PostMapping(path = "/admins", version = "1.0")
    public ResponseEntity<User> createAdmin(@RequestBody RegisterAdminRequest registerAdminRequest){

        return ResponseEntity.ok().body(adminService.createSuperAdmin(registerAdminRequest));
    }

    @DeleteMapping(path = "/admins/{adminId}", version = "1.0")
    public ResponseEntity<Void> deleteAdminById(@PathVariable Long adminId){

        adminService.deleteAdminById(adminId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/admins/{adminId}/status", version = "1.0")
    public ResponseEntity<Void> updateAdminStatus(@PathVariable Long adminId, @RequestBody String status){

        adminService.changeSuperAdminStatus(adminId, status);

        return ResponseEntity.noContent().build();
    }
}
