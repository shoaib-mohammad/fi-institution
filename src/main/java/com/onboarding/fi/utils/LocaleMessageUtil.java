package com.onboarding.fi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
public final class LocaleMessageUtil implements MessageSourceAware {

    @Autowired
    private static MessageSource messageSource;

    public static String getMessageByKey(String key, @Nullable String... args) {
        log.debug("Getting translation to key: ".concat(key));
        String msg = key;
        if (!CommonUtils.isEmpty(key)) {
            Locale locale = LocaleContextHolder.getLocale();
            try {
                msg = messageSource.getMessage(key, args, locale);
            } catch (Exception ex) {
                log.warn("No translation found for key: ".concat(key).concat(" using language: ".concat(locale.getLanguage())), ex);
            }
            log.debug("Translation to key: ".concat(key).concat(" is: ").concat(msg));
        } else {
            msg = "";
            log.warn("name parameter is empty or null");
        }
        return msg;
    }

    public static String getMessageByKeyAndLocale(String key, Locale locale, @Nullable String... args) {
        log.debug("Getting translation to key: ".concat(key).concat(" by language: ").concat(locale.getLanguage()));
        String msg = key;
        if (!CommonUtils.isEmpty(key)) {
            if (null == locale) {
                locale = LocaleContextHolder.getLocale();
            }
            try {
                msg = messageSource.getMessage(key, args, locale);
            } catch (Exception ex) {
                log.warn("No translation found for key: ".concat(key).concat(" using language: ".concat(locale.getLanguage())), ex);
            }
            log.debug("Translation to key: ".concat(key).concat(" is: ").concat(msg).concat(" by language: ").concat(locale.getLanguage()));
        } else {
            msg = "";
            log.warn("name parameter is empty or null");
        }
        return msg;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        LocaleMessageUtil.messageSource = messageSource;
    }
}