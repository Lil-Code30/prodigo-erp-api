package com.licode.prodigoerp.user.entity;

import com.licode.prodigoerp.module.entity.Module;
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
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Size(max = 50)
    @NotNull
    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Size(max = 100)
    @NotNull
    @Column(name = "resource", nullable = false, length = 100)
    private String resource;


    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "module_id")
    private Module module;

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