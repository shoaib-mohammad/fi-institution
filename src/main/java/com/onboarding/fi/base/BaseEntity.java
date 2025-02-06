package com.onboarding.fi.base;

import com.onboarding.fi.enums.CustomEnums;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity implements Cloneable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CustomEnums.Status status;

    @Column(name = "date_created", nullable = false)
    @CreationTimestamp
    private LocalDate dateCreated;

    protected BaseEntity() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
