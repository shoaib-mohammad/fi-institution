package com.onboarding.fi.institution.services.impl;

import com.onboarding.fi.enums.CustomEnums;
import com.onboarding.fi.enums.ErrorResponseApisEnum;
import com.onboarding.fi.exceptions.CustomServiceException;
import com.onboarding.fi.institution.dtos.InstitutionRequestDTO;
import com.onboarding.fi.institution.models.Institution;
import com.onboarding.fi.institution.repositories.InstitutionRepository;
import com.onboarding.fi.institution.services.InstitutionService;
import com.onboarding.fi.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

    private final ModelMapper modelMapper;

    private final InstitutionRepository institutionRepository;

    @Override
    public Class getModelClass() {
        return Institution.class;
    }

    @Override
    public InstitutionRepository getRepository() {
        return institutionRepository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Override
    public Institution saveOrUpdate(InstitutionRequestDTO institutionRequestDTO) throws CustomServiceException {
        if (institutionRequestDTO.getId() == null || institutionRequestDTO.getId() == 0) {
            log.info("Savinginstitution " + institutionRequestDTO.toString());
            validateInstitutionOnSave(institutionRequestDTO);
            return getRepository().save(getModelMapper().map(institutionRequestDTO, Institution.class));
        }
        return updateInstitution(institutionRequestDTO.getId(), institutionRequestDTO);
    }

    private void validateInstitutionOnSave(InstitutionRequestDTO institutionRequestDTO) throws CustomServiceException {
        if (CommonUtils.isEmpty(institutionRequestDTO.getName())) {
            throw new CustomServiceException(ErrorResponseApisEnum.MissingParameter, "cannot.create.entity.without.providing.key", new String[]{"institution.key", "name.key"});
        }
        if (CommonUtils.isEmpty(institutionRequestDTO.getCode())) {
            throw new CustomServiceException(ErrorResponseApisEnum.MissingParameter, "cannot.create.entity.without.providing.key", new String[]{"institution.key", "code.key"});
        }
        checkExistenceInstitution(institutionRequestDTO.getCode());
    }

    private void checkExistenceInstitution(String code) throws CustomServiceException {
        Institution checkExistenceInstitution = institutionRepository.findByCode(code);
        if (checkExistenceInstitution != null) {
            throw new CustomServiceException(ErrorResponseApisEnum.BadRequest, "unique.institution.code.key", new String[]{});
        }
    }

    private Institution updateInstitution(Long id, InstitutionRequestDTO institutionRequestDTO) throws CustomServiceException {
        log.info("Updating institution " + institutionRequestDTO.toString());
        if (null == id || CommonUtils.isEmpty(id.toString())) {
            throw new CustomServiceException(ErrorResponseApisEnum.MissingParameter, "cannot.be.null.or.empty.key", new String[]{"id.key"});
        }
        Institution institution = getRepository().findById(id)
                .orElseThrow(() -> new CustomServiceException(ErrorResponseApisEnum.ItemNotFound, "no.entity.found.with.id.key", new String[]{"institution.key", id.toString()}));
        validateInstitutionOnUpdate(institutionRequestDTO, institution);
        return getRepository().save(institution);
    }

    private void validateInstitutionOnUpdate(InstitutionRequestDTO institutionRequestDTO, Institution institution) throws CustomServiceException {
        if (!CommonUtils.isEmpty(institutionRequestDTO.getName()) && !institutionRequestDTO.getName().equals(institution.getName())) {
            institution.setName(institutionRequestDTO.getName());
        }
        if (!CommonUtils.isEmpty(institutionRequestDTO.getCode()) && !institutionRequestDTO.getCode().equals(institution.getCode())) {
            checkExistenceInstitution(institutionRequestDTO.getCode());
            institution.setCode(institutionRequestDTO.getCode());
        }
        if (null != institutionRequestDTO.getCurrencyList() && !institutionRequestDTO.getCurrencyList().isEmpty()) {
            institution.setCurrencyList(institutionRequestDTO.getCurrencyList());
        } else if (null == institutionRequestDTO.getCurrencyList()) {
            institution.setCurrencyList(null);
        }
        if (null != institutionRequestDTO.getStatus() && !institutionRequestDTO.getStatus().equals(institution.getStatus())) {
            institution.setStatus(institutionRequestDTO.getStatus());
        }
    }

    @Override
    public List<Institution> getActiveInstitutions() {
        return institutionRepository.findByStatus(CustomEnums.Status.ACTIVE);
    }
}
