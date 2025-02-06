package com.onboarding.fi.exceptions;

import com.onboarding.fi.enums.ErrorResponseApisEnum;
import com.onboarding.fi.utils.CommonUtils;
import com.onboarding.fi.utils.LocaleMessageUtil;
import lombok.Getter;

import java.util.Locale;

@Getter
public class CustomServiceException extends Exception {

    private final ErrorResponseApisEnum errorResponseApiEnum;
    private final String transMessage;
    private String devMessage;

    public CustomServiceException(ErrorResponseApisEnum errorResponseApisEnum, String... messageTrsArgs) {
        super(LocaleMessageUtil.getMessageByKeyAndLocale(returnDefaultErrorResponseApi(errorResponseApisEnum).getMessage(), Locale.US, translate(messageTrsArgs, Locale.US)));
        errorResponseApisEnum = returnDefaultErrorResponseApi(errorResponseApisEnum);
        this.errorResponseApiEnum = errorResponseApisEnum;
        this.transMessage = LocaleMessageUtil.getMessageByKey(errorResponseApisEnum.getMessage(), translate(messageTrsArgs, null));
    }

    public CustomServiceException(ErrorResponseApisEnum errorResponseApisEnum, String messageKey, String... messageTrsArgs) {
        super(LocaleMessageUtil.getMessageByKeyAndLocale(messageKey, Locale.US, translate(messageTrsArgs, Locale.US)));
        this.transMessage = LocaleMessageUtil.getMessageByKey(messageKey, translate(messageTrsArgs, null));
        errorResponseApisEnum = returnDefaultErrorResponseApi(errorResponseApisEnum);
        this.errorResponseApiEnum = errorResponseApisEnum;
    }

    private static ErrorResponseApisEnum returnDefaultErrorResponseApi(ErrorResponseApisEnum errorResponseApiEnum) {
        return (null == errorResponseApiEnum ? ErrorResponseApisEnum.ServerError : errorResponseApiEnum);
    }

    private static String[] translate(String[] keys, Locale locale) {
        if (null == keys || keys.length == 0) {
            return keys;
        }
        String[] transArray = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            if (!CommonUtils.isEmpty(key) && key.endsWith(".key")) {
                if (null == locale) {
                    transArray[i] = LocaleMessageUtil.getMessageByKey(key);
                } else {
                    transArray[i] = LocaleMessageUtil.getMessageByKeyAndLocale(key, locale);
                }
            } else {
                transArray[i] = key;
            }
        }
        return transArray;
    }
}