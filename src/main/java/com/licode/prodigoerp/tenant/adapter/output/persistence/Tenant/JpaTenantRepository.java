package com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface JpaTenantRepository extends JpaRepository<TenantJpaEntity, Long> {

    boolean existsBySlug(String slug);
    Optional<TenantJpaEntity> findBySlug(String slug);

    @Modifying
    @Transactional
    @Query("UPDATE TenantJpaEntity t SET t.status = :status WHERE t.slug = :slug")
    void changeStatus(@NotBlank String slug, @NotBlank @Param("status") String status);

}
