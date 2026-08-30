package com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity;

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
@Table(name = "role_permissions")
public class RolePermissionJpaEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false,  updatable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleJpaEntity roleJpaEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionJpaEntity permissionJpaEntity;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "granted_at", nullable = false)
    private Instant grantedAt;

    @Size(max = 100)
    @NotNull
    @Column(name = "granted_by", nullable = false, length = 100)
    private String grantedBy;


}