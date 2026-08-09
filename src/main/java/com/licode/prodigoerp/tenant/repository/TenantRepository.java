package com.licode.prodigoerp.tenant.repository;

import com.licode.prodigoerp.tenant.entity.Tenant;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    boolean existsBySlug(String slug);
    Optional<Tenant> findBySlug(String slug);

    @Modifying
    @Transactional
    @Query("UPDATE Tenant t SET t.isActive = :status WHERE t.slug = :slug")
    void changeStatus(@NotBlank String slug, @NotBlank @Param("status") boolean status);
    
}
