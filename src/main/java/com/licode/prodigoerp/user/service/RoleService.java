package com.licode.prodigoerp.user.service;

import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.common.exception.NotFoundException;
import com.licode.prodigoerp.tenant.entity.Tenant;
import com.licode.prodigoerp.user.entity.Role;
import com.licode.prodigoerp.user.entity.User;
import com.licode.prodigoerp.user.entity.UserRole;
import com.licode.prodigoerp.user.repository.RoleRepository;
import com.licode.prodigoerp.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Transactional
    public Role createAdminRole(Tenant tenant) {
        String roleName = "ADMIN";
        Instant now = Instant.now();

        Role role = new Role();

        role.setName(roleName);
        role.setDescription("ROLE_ADMIN : This is the role that has full access to the Tenant (Company)");
        role.setTenant(tenant);
        role.setIsDefault(false);

        role.setCreatedAt(now);
        role.setUpdatedAt(now);
        role.setUpdatedBy(SystemConstants.SYSTEM_NAME);
        role.setCreatedBy(SystemConstants.SYSTEM_NAME);


        return roleRepository.save(role);
    }

    @Transactional
    public Role createSuperAdminRole() {
        String roleName = "SUPER_ADMIN";
        Instant now = Instant.now();

        Role role = new Role();
        role.setName(roleName);
        role.setDescription("Role::SUPER_ADMIN : This is the role that has full access to the System (ERP");
        role.setTenant(null);
        role.setIsDefault(false);

        role.setCreatedAt(now);
        role.setUpdatedAt(now);
        role.setUpdatedBy(SystemConstants.SYSTEM_NAME);
        role.setCreatedBy(SystemConstants.SYSTEM_NAME);

        return roleRepository.save(role);
    }

    @Transactional
    public void assignedRoleToUser(User user, Role role, Long tenantId, String assignedBy) {

        UserRole userRole = new UserRole();

        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setTenantId(tenantId);
        userRole.setAssignedBy(assignedBy);
        userRole.setAssignedAt(Instant.now());

        userRoleRepository.save(userRole);
    }

    public boolean roleExists(String roleName) {
        return roleRepository.existsByName(roleName);
    }

    public Role getRoleByName(String roleName) {
       Optional<Role> role =  roleRepository.findRoleByName(roleName);

       if(role.isEmpty()) {
           throw new NotFoundException("Role provided is not found");
       }

       return role.get();
    }
}
