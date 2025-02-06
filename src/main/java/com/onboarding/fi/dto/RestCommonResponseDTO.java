package com.onboarding.fi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onboarding.fi.utils.LocaleMessageUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class RestCommonResponseDTO<RES> implements Serializable {

    public RES data;
    private boolean valid;
    private String title;

    private String message;

    private Integer errorCode;

    private String devMessage;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String stackTrace;

    public RestCommonResponseDTO(boolean valid) {
        this.valid = valid;
        if (valid) {
            this.title = LocaleMessageUtil.getMessageByKey("success.key");
            this.message = LocaleMessageUtil.getMessageByKey("success.key");
        }
    }

    public RestCommonResponseDTO(boolean valid, RES data) {
        this.valid = valid;
        this.data = data;
        if (valid) {
            this.title = LocaleMessageUtil.getMessageByKey("success.key");
            this.message = LocaleMessageUtil.getMessageByKey("success.key");
        }
    }
}