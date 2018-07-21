package com.team5.dl;

public enum Properties {

    PROPERTY("property"),

    ;

    private String value;

    Properties(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
