package com.licode.prodigoerp.auth.adapter.output.persistence.Authority.entity;

import com.licode.prodigoerp.auth.adapter.output.persistence.user.UserJpaEntity;
import com.licode.prodigoerp.auth.domain.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_roles")
public class UserRoleJpaEntity {
    @Id
    @Column(name = "id", nullable = false,   updatable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity userJpaEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleJpaEntity roleJpaEntity;

    @Column(name = "tenant_id")
    private UUID tenantId;

    @Size(max = 100)
    @NotNull
    @Column(name = "assigned_by", nullable = false, length = 100)
    private String assignedBy;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;


}