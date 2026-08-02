package com.licode.prodigoerp.user.entity;

import com.licode.prodigoerp.tenant.entity.Tenant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Size(max = 20)
    @NotNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "isDefault", nullable = false)
    private Boolean isDefault;

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