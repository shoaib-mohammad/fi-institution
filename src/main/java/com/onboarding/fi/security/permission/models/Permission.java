package com.onboarding.fi.security.permission.models;

import com.onboarding.fi.base.BaseEntity;
import com.onboarding.fi.security.role.models.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "permission", uniqueConstraints = {@UniqueConstraint(name = "uc_name_role", columnNames = {"name", "role_id"})})
public class Permission extends BaseEntity {

    @NotBlank(message = "{should.not.be.empty.key}")
    @Column(name = "name")
    private String name;

    @NotNull(message = "{cannot.be.null.key}")
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinColumn(name = "role_id", unique = true, nullable = false)
    private Role role;
}
