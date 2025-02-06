package com.onboarding.fi.converters;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.commons.CommonUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
@Converter
public class ListToStringConverter implements AttributeConverter<List<String>, String> {

    public String convertToDatabaseColumn(List<String> stringList) {
        String stringConverted = null;
        if (null != stringList && !stringList.isEmpty()) {
            stringConverted = stringList.stream().collect(Collectors.joining(","));
            log.debug("List Converted ".concat(stringConverted));
        }
        return stringConverted;
    }

    public List<String> convertToEntityAttribute(String stringConverted) {
        List<String> stringList = null;
        if (!CommonUtils.isEmpty(stringConverted)) {
            stringList = Arrays.asList(stringConverted.split(","));
        }
        return stringList;
    }
}
