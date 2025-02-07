package com.onboarding.fi.exceptions;

import com.onboarding.fi.dto.RestCommonResponseDTO;
import com.onboarding.fi.enums.ErrorResponseApisEnum;
import com.onboarding.fi.utils.CommonUtils;
import com.onboarding.fi.utils.LocaleMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(CustomServiceException.class)
    public final ResponseEntity<RestCommonResponseDTO> handleCustomServiceException(CustomServiceException ex) {
        log.error("CustomServiceException Error", ex);
        RestCommonResponseDTO exceptionResponse = new RestCommonResponseDTO(false);
        ErrorResponseApisEnum errorResponseApisEnum = (null == ex.getErrorResponseApiEnum() ? ErrorResponseApisEnum.ServerError : ex.getErrorResponseApiEnum());
        exceptionResponse.setErrorCode(errorResponseApisEnum.getErrorCode());
        exceptionResponse.setMessage(ex.getTransMessage());
        exceptionResponse.setTitle(LocaleMessageUtil.getMessageByKey(errorResponseApisEnum.getTitle()));
        exceptionResponse.setDevMessage(ex.getDevMessage());
        return new ResponseEntity<>(exceptionResponse, errorResponseApisEnum.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<RestCommonResponseDTO> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access Denied Exception", ex);
        RestCommonResponseDTO exceptionResponse = new RestCommonResponseDTO(false);
        ErrorResponseApisEnum errorResponseApisEnum = ErrorResponseApisEnum.Unauthorized;
        exceptionResponse.setErrorCode(errorResponseApisEnum.getErrorCode());
        exceptionResponse.setMessage(LocaleMessageUtil.getMessageByKey(errorResponseApisEnum.getMessage()));
        exceptionResponse.setTitle(LocaleMessageUtil.getMessageByKey(errorResponseApisEnum.getTitle()));
        return new ResponseEntity<>(exceptionResponse, errorResponseApisEnum.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<RestCommonResponseDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Constraint Violation Exception", ex);
        RestCommonResponseDTO exceptionResponse = new RestCommonResponseDTO(false);
        ErrorResponseApisEnum errorResponseApisEnum = ErrorResponseApisEnum.ServerError;
        exceptionResponse.setErrorCode(errorResponseApisEnum.getErrorCode());
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setTitle(LocaleMessageUtil.getMessageByKey(errorResponseApisEnum.getTitle()));
        return new ResponseEntity<>(exceptionResponse, errorResponseApisEnum.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<RestCommonResponseDTO> handleException(Exception ex) {
        log.error("Exception Error", ex);
        RestCommonResponseDTO exceptionResponse = new RestCommonResponseDTO(false);
        ErrorResponseApisEnum errorResponseApisEnum = ErrorResponseApisEnum.ServerError;
        exceptionResponse.setErrorCode(errorResponseApisEnum.getErrorCode());
        if (null != ex.getCause() && !CommonUtils.isEmpty(ex.getCause().getMessage())) {
            exceptionResponse.setMessage(ex.getCause().getMessage());
        } else if (!CommonUtils.isEmpty(ex.getMessage())) {
            exceptionResponse.setMessage(ex.getMessage());
        } else {
            exceptionResponse.setMessage(LocaleMessageUtil.getMessageByKey(errorResponseApisEnum.getMessage()));
        }
        exceptionResponse.setTitle(LocaleMessageUtil.getMessageByKey(errorResponseApisEnum.getTitle()));
        return new ResponseEntity<>(exceptionResponse, errorResponseApisEnum.getHttpStatus());
    }
}