package com.onboarding.fi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CustomEnums {
    @Getter
    @AllArgsConstructor
    public enum Status {
        INACTIVE(0),
        ACTIVE(1);
        private final int value;
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        ADMIN("Admin"), USER("User");
        private final String name;
    }
}
