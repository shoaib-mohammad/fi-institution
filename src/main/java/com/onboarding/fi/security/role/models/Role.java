package com.onboarding.fi.security.role.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onboarding.fi.base.BaseEntity;
import com.onboarding.fi.enums.CustomEnums;
import com.onboarding.fi.security.permission.models.Permission;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @NotNull(message = "{cannot.be.null.key}")
    @Column(name = "role_type", columnDefinition = "TEXT", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomEnums.Type roleType;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<Permission> permissions;

}
