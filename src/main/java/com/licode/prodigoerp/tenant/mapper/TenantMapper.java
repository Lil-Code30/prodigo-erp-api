package com.licode.prodigoerp.tenant.mapper;

import com.licode.prodigoerp.tenant.entity.Tenant;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
public class TenantMapper {

    static public Tenant toEntity(String name, String slug, String country, String username, Instant now) {

        Tenant tenant = new Tenant();

        tenant.setName(name);
        tenant.setSlug(slug);
        tenant.setCreatedBy(username);
        tenant.setIsActive(true);
        tenant.setCountry(country);
        tenant.setUpdatedBy(username);
        tenant.setCreatedAt(now);
        tenant.setUpdatedAt(now);

        return tenant;

    }
}
