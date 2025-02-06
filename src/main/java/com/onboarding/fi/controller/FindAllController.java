package com.onboarding.fi.controller;

import com.onboarding.fi.dto.RestCommonResponseDTO;
import com.onboarding.fi.exceptions.CustomServiceException;
import com.onboarding.fi.services.FindAllService;
import com.onboarding.fi.utils.CommonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface FindAllController<RES, S extends FindAllService> {

    S getService();

    Class getResponseClass();

    String findAllPermission();

    @GetMapping
    @ApiOperation("Find all instances")
    default RestCommonResponseDTO<List<RES>> findAll() throws CustomServiceException {
        CommonUtils.checkUserPermissions(findAllPermission());
        return new RestCommonResponseDTO(true, CommonUtils.mapList(getService().findAll(), getResponseClass()));
    }
}