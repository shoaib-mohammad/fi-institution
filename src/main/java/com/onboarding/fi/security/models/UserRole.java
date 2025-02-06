package com.onboarding.fi.security.models;

import com.onboarding.fi.base.BaseEntity;
import com.onboarding.fi.security.role.models.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "user_role", uniqueConstraints = {@UniqueConstraint(name = "uc_user_role", columnNames = {"role_id", "user_id"})})
public class UserRole extends BaseEntity implements GrantedAuthority {
    @NotNull(message = "{cannot.be.null.key}")
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @NotNull(message = "{cannot.be.null.key}")
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public String getAuthority() {
        return role.getPermissions().stream().map(p -> p.getName()).collect(Collectors.joining(","));
    }
}
