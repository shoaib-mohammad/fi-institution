package com.onboarding.fi.controller;

import com.onboarding.fi.base.Identifiable;
import com.onboarding.fi.dto.RestCommonResponseDTO;
import com.onboarding.fi.enums.ErrorResponseApisEnum;
import com.onboarding.fi.exceptions.CustomServiceException;
import com.onboarding.fi.services.CUDService;
import com.onboarding.fi.utils.CommonUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface CUDController<REQ, RES, S extends CUDService> {

    S getService();

    Class getResponseClass();

    ModelMapper getModelMapper();

    String saveOrUpdatePermission();

    String deletePermission();

    @PostMapping
    @ApiOperation("Create or update instance")
    default ResponseEntity<RestCommonResponseDTO<RES>> saveOrUpdate(@RequestBody @ApiParam(value = "Instance data") @Valid REQ req) throws CustomServiceException {
        CommonUtils.checkUserPermissions(saveOrUpdatePermission());

        RES response = (RES) getModelMapper().map(getService().saveOrUpdate(req), getResponseClass());

        // Check if ID is null or 0
        boolean isNew = (req instanceof Identifiable &&
                (((Identifiable) req).getId() == null || ((Identifiable) req).getId() == 0));

        if (isNew) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new RestCommonResponseDTO<>(true, response));
        } else {
            return ResponseEntity.ok(new RestCommonResponseDTO<>(true, response));
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete an existing instance")
    default RestCommonResponseDTO delete(@PathVariable @ApiParam(value = "Id of instance that should be deleted") Long id) throws CustomServiceException {
        CommonUtils.checkUserPermissions(deletePermission());
        if (null == id || CommonUtils.isEmpty(id.toString())) {
            throw new CustomServiceException(ErrorResponseApisEnum.MissingParameter, "cannot.be.null.or.empty.key", new String[]{"id.key"});
        }
        getService().delete(id);
        return new RestCommonResponseDTO(true);
    }

}