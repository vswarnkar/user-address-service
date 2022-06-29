package com.scl.gt.model;

public enum Gender {

    MALE("Male"),
    FEMALE("Female");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }

}
