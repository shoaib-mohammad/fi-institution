package com.onboarding.fi.institution.models;

import com.onboarding.fi.base.BaseEntity;
import com.onboarding.fi.converters.ListToStringConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "institution")
public class Institution extends BaseEntity {
    @NotBlank(message = "{should.not.be.empty.key}")
    @Column(nullable = false, columnDefinition = "TEXT")
    @Length(message = "{value.length.should.be.between.key}", min = 1, max = 50)
    private String name;

    @Column(name = "currency", columnDefinition = "TEXT")
    @Convert(converter = ListToStringConverter.class)
    private List<String> currencyList;

    @NotBlank(message = "{should.not.be.empty.key}")
    @Column(name = "code", nullable = false, unique = true, columnDefinition = "TEXT")
    @Length(message = "{value.length.should.be.between.key}", min = 1, max = 5)
    private String code;
}
