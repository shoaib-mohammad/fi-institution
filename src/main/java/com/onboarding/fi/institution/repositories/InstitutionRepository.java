package com.onboarding.fi.institution.repositories;

import com.onboarding.fi.enums.CustomEnums;
import com.onboarding.fi.institution.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long>, JpaSpecificationExecutor<Institution> {
    Institution findByCode(String code);

    List<Institution> findByStatus(CustomEnums.Status status);
}
