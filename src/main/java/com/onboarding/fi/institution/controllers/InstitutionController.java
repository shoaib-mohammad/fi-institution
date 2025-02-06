package com.onboarding.fi.institution.controllers;

import com.onboarding.fi.constants.PermissionConstant;
import com.onboarding.fi.controller.CUDController;
import com.onboarding.fi.controller.FindAllController;
import com.onboarding.fi.controller.FindByIdController;
import com.onboarding.fi.dto.RestCommonResponseDTO;
import com.onboarding.fi.institution.dtos.InstitutionRequestDTO;
import com.onboarding.fi.institution.dtos.InstitutionResponseDTO;
import com.onboarding.fi.institution.services.InstitutionService;
import com.onboarding.fi.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/institution")
@RequiredArgsConstructor
public class InstitutionController implements CUDController<InstitutionRequestDTO, InstitutionResponseDTO, InstitutionService>, FindByIdController<InstitutionResponseDTO, InstitutionService>, FindAllController<InstitutionResponseDTO, InstitutionService> {

    private final InstitutionService institutionService;

    private final ModelMapper modelMapper;

    @Override
    public InstitutionService getService() {
        return institutionService;
    }

    @Override
    public Class getResponseClass() {
        return InstitutionResponseDTO.class;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Override
    public String saveOrUpdatePermission() {
        return PermissionConstant.ROLE_CU_F_INSTITUTION;
    }

    @Override
    public String deletePermission() {
        return PermissionConstant.ROLE_D_INSTITUTION;
    }

    @Override
    public String findByIdPermission() {
        return PermissionConstant.ROLE_CU_F_INSTITUTION;
    }

    @Override
    public String findAllPermission() {
        return PermissionConstant.ROLE_CU_F_INSTITUTION;
    }

    @PreAuthorize("hasRole('" + PermissionConstant.ROLE_CU_F_INSTITUTION + "')")
    @GetMapping("/active")
    RestCommonResponseDTO<List<InstitutionResponseDTO>> getActiveInstitutions() {
        return new RestCommonResponseDTO<>(true, CommonUtils.mapList(institutionService.getActiveInstitutions(), InstitutionResponseDTO.class));
    }
}