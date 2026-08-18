package com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant;


import com.licode.prodigoerp.tenant.adapter.input.rest.dto.RegisterTenantDto;
import com.licode.prodigoerp.tenant.application.port.output.TenantCommandRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TenantCommandAdapter implements TenantCommandRepositoryPort {

    private final JpaTenantRepository jpaTenantRepository;


    @Override
    public TenantJpaEntity createNewTenant(RegisterTenantDto registerTenantDto) {

        return jpaTenantRepository.save(TenantJpaMapper.toDbCreateTenant(registerTenantDto));
    }
}
