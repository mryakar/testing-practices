package me.mryakar.tp.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE,
    FEMALE;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
