package com.licode.prodigoerp.tenant.adapter.output.persistence.TenantEntitlement;

import com.licode.prodigoerp.tenant.adapter.output.persistence.Tenant.TenantJpaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tenant_entitlements")
public class TenantEntitlementJpaEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false,  updatable = false)
    private UUID id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tenant_id", nullable = false)
    private TenantJpaEntity tenantJpaEntity;

    @NotNull
    @ColumnDefault("5")
    @Column(name = "max_users", nullable = false)
    private Integer maxUsers;

    @NotNull
    @ColumnDefault("5")
    @Column(name = "max_storage_gb", nullable = false)
    private Integer maxStorageGb;

    @NotNull
    @ColumnDefault("20")
    @Column(name = "max_products", nullable = false)
    private Long maxProducts;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Size(max = 100)
    @NotNull
    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @Size(max = 100)
    @NotNull
    @Column(name = "updated_by", nullable = false, length = 100)
    private String updatedBy;


}