package com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant;

import com.licode.prodigoerp.tenant.application.port.output.TenantQueryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TenantQueryAdapter implements TenantQueryRepositoryPort {

    private final JpaTenantRepository jpaTenantRepository;

    @Override
    public Optional<TenantJpaEntity> findTenantById(Long id) {
        return jpaTenantRepository.findById(id);
    }

    @Override
    public Optional<TenantJpaEntity> findTenantBySlug(String slug) {
        return jpaTenantRepository.findBySlug(slug);
    }

    @Override
    public Boolean existsBySlug(String slug) {
        return jpaTenantRepository.existsBySlug(slug);
    }
}
