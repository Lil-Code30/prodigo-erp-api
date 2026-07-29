package com.licode.prodigoerp.tenant.service;

import com.licode.prodigoerp.tenant.entity.Tenant;
import com.licode.prodigoerp.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }
}
