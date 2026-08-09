package com.licode.prodigoerp.tenant.service;

import com.licode.prodigoerp.common.exception.NotFoundException;
import com.licode.prodigoerp.tenant.entity.Tenant;
import com.licode.prodigoerp.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant save(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Tenant getTenantBySlug(String slug) {

        Optional<Tenant> optionalTenant = tenantRepository.findBySlug(slug);

        if (optionalTenant.isEmpty()) {
           throw new NotFoundException("No Tenant with slug " + slug);
        }

        return optionalTenant.get();
    }

    public Tenant getTenantById(Long id) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(id);

        if (optionalTenant.isEmpty()) {
            throw new NotFoundException("No Tenant with Id: " + id);
        }

        return optionalTenant.get();
    }

    public void changeTenantStatus(String slug, Boolean status) {

        // first check if a Tenant with this slug exist
        if(!tenantRepository.existsBySlug(slug)) {
            throw new NotFoundException("No tenant with slug " + slug);
        }

        tenantRepository.changeStatus(slug, status);
    }


}
