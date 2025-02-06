package com.onboarding.fi.institution.services;

import com.onboarding.fi.institution.dtos.InstitutionRequestDTO;
import com.onboarding.fi.institution.models.Institution;
import com.onboarding.fi.institution.repositories.InstitutionRepository;
import com.onboarding.fi.services.CUDService;
import com.onboarding.fi.services.FindAllService;
import com.onboarding.fi.services.FindByIdService;

import java.util.List;

public interface InstitutionService extends CUDService<Institution, InstitutionRequestDTO, InstitutionRepository>, FindByIdService<Institution, InstitutionRepository>, FindAllService<Institution, InstitutionRepository> {
    List<Institution> getActiveInstitutions();
}
