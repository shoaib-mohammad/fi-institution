package com.onboarding.fi.utils;

import com.onboarding.fi.configs.CustomSpringContext;
import com.onboarding.fi.enums.ErrorResponseApisEnum;
import com.onboarding.fi.exceptions.CustomServiceException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CommonUtils {

    public static boolean isEmpty(final String str) {
        return null == str || str.length() == 0;
    }

    public static <S, D> List<D> mapList(List<S> source, Class<D> targetClass) {
        ModelMapper modelMapper = CustomSpringContext.getBean(ModelMapper.class);
        return source.stream().map((element) -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
    }

    public static void checkUserPermissions(String permission) throws CustomServiceException {
        log.info("Check user permission");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || !authentication.getAuthorities().stream().anyMatch((a) -> {
            return a.getAuthority().equals(permission);
        })) {
            throw new CustomServiceException(ErrorResponseApisEnum.AccessDenied);
        }
    }
}
