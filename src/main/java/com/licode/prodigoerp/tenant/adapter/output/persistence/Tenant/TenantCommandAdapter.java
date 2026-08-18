package com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant;


import com.licode.prodigoerp.tenant.adapter.input.rest.dto.RegisterTenantDto;
import com.licode.prodigoerp.tenant.application.port.output.TenantCommandRepositoryPort;
import com.licode.prodigoerp.tenant.domain.command.CreateTenantCommand;
import com.licode.prodigoerp.tenant.domain.model.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TenantCommandAdapter implements TenantCommandRepositoryPort {

    private final JpaTenantRepository jpaTenantRepository;

    @Override
    public Tenant createNewTenant(CreateTenantCommand createTenantCommand) {
        RegisterTenantDto registerTenantDto = new RegisterTenantDto(
                createTenantCommand.companyName(),
                createTenantCommand.companySlug(),
                createTenantCommand.country()
        );


        TenantJpaEntity createdTenantJpa =  jpaTenantRepository.save(TenantJpaMapper.toDbCreateTenant(registerTenantDto));

        return TenantJpaMapper.toTenantModel(createdTenantJpa);
    }
}
