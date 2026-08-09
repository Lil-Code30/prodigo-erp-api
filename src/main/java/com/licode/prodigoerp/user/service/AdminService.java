package com.licode.prodigoerp.user.service;


import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.common.exception.BadRequestException;
import com.licode.prodigoerp.common.exception.ConflictException;
import com.licode.prodigoerp.common.exception.NotFoundException;
import com.licode.prodigoerp.user.dto.CreatePermission;
import com.licode.prodigoerp.user.dto.RegisterAdminRequest;
import com.licode.prodigoerp.user.entity.Permission;
import com.licode.prodigoerp.user.entity.Role;
import com.licode.prodigoerp.user.entity.User;
import com.licode.prodigoerp.user.mapper.UserMapper;
import com.licode.prodigoerp.user.repository.AdminRepository;
import com.licode.prodigoerp.user.repository.RoleRepository;
import com.licode.prodigoerp.user.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PermissionService permissionService;

    public List<User> getAllSuperAdmin(Boolean isSuperAdmin){
        return adminRepository.findAllByIsSuperAdmin(isSuperAdmin);
    }

    public User getSuperAdminById(Long adminId){
        Optional<User> optionalUser =  adminRepository.findById(adminId);

        if(optionalUser.isEmpty()){
            throw new NotFoundException("Admin not found with id: " + adminId);
        }

        return optionalUser.get();
    }

    public void deleteAdminById(Long adminId){

        if(!adminRepository.existsById(adminId)){
            throw new NotFoundException("Admin not found with id: " + adminId);
        }

        // SOFT DELETE here
        adminRepository.changeAdminStatus(adminId, "DEACTIVATED");
        //adminRepository.deleteById(adminId);
    }

    public void changeSuperAdminStatus(@NotBlank Long adminId, @NotBlank String status) {
        if(!adminRepository.existsById(adminId)){
            throw new NotFoundException("Admin not found with id: " + adminId);
        }

        if(!SystemConstants.STATUS_LIST.contains(status)){
            throw new BadRequestException("The Status provided " + status + " is not a valid");
        }

        adminRepository.changeAdminStatus(adminId, status);
    }

    @Transactional
    public User createSuperAdmin(RegisterAdminRequest registerAdminRequest){

        if(userRepository.existsByUsername(registerAdminRequest.username())){
            throw new ConflictException("The username provided " + registerAdminRequest.username() + " already exists");
        }

        if(userRepository.existsByEmail(registerAdminRequest.email())){
            throw new ConflictException("The email provided " + registerAdminRequest.email() + " already exists");
        }

        Instant now = Instant.now();

        User superAdmin =  userRepository.save(userMapper.toAdminEntity(registerAdminRequest, now));

        // After the creation of the admin, we need to assign the "superadmin" role
        // This is what really determine if the user is a superadmin
        // Also need to check if the role already exists inorder not to creat the role every time

        if(roleService.roleExists("SUPERADMIN")){
            Role role = roleService.getRoleByName("SUPERADMIN");

            roleService.assignedRoleToUser(superAdmin, role, null, SystemConstants.SYSTEM_NAME);
        }else{
            // We need to create the permission to grant all access to the system (ERP)
            CreatePermission permissionDto = new CreatePermission(
                    "Complete (FULL) Access to the ERP System",
                    "CRUD",
                    "ERP",
                    null

            );

            Permission permission = permissionService.createPermission(permissionDto);

            Role superAdminRole = roleService.createSuperAdminRole();

            roleService.assignedRoleToUser(superAdmin, superAdminRole, null, SystemConstants.SYSTEM_NAME);

            // now we need to assigne the permission to the role created
            permissionService.assignPermissionToRole(permission, superAdminRole);
        }

        return adminRepository.save(superAdmin);
    }

}
