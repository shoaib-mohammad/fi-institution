package com.onboarding.fi.controller;

import com.onboarding.fi.constants.CommonConstants;
import com.onboarding.fi.dto.RestCommonResponseDTO;
import com.onboarding.fi.enums.ErrorResponseApisEnum;
import com.onboarding.fi.exceptions.CustomServiceException;
import com.onboarding.fi.services.FindByIdService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jasypt.commons.CommonUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.Optional;

public interface FindByIdController<RES, S extends FindByIdService> {

    S getService();

    Class getResponseClass();

    ModelMapper getModelMapper();

    String findByIdPermission();

    @GetMapping("/{id}")
    @ApiOperation("Find instance by id")
    default RestCommonResponseDTO<RES> findById(@PathVariable @ApiParam(value = "Id of the instance that will be retrieved") Long id) throws CustomServiceException {
        com.onboarding.fi.utils.CommonUtils.checkUserPermissions(findByIdPermission());
        if (null == id || CommonUtils.isEmpty(id.toString())) {
            throw new CustomServiceException(ErrorResponseApisEnum.MissingParameter, "cannot.be.null.or.empty.key", new String[]{"id.key"});
        }
        Optional optionalEntity = getService().findById(id);
        Object obj = null;
        if (!optionalEntity.isEmpty()) {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration()
                    .setFieldMatchingEnabled(getModelMapper().getConfiguration().isFieldMatchingEnabled())
                    .setSkipNullEnabled(getModelMapper().getConfiguration().isSkipNullEnabled())
                    .setAmbiguityIgnored(getModelMapper().getConfiguration().isAmbiguityIgnored())
                    .setFieldAccessLevel(getModelMapper().getConfiguration().getFieldAccessLevel())
                    .setSourceNamingConvention(getModelMapper().getConfiguration().getSourceNamingConvention());
            Converter<String, Long> toString = context -> context.getSource() == null ? null : Long.valueOf(context.getSource());

            Converter<String, LocalDate> toStringDate = new AbstractConverter<String, LocalDate>() {
                @Override
                protected LocalDate convert(String source) {
                    LocalDate localDate = LocalDate.parse(source, CommonConstants.DATE_TIME_FORMATTER);
                    return localDate;
                }
            };

            mapper.addConverter(toString);
            mapper.addConverter(toStringDate);
            obj = mapper.map(optionalEntity.get(), getResponseClass());
        }

        return new RestCommonResponseDTO(true, obj);
    }
}