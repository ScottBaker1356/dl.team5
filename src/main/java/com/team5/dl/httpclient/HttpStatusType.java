package com.team5.dl.httpclient;

public enum HttpStatusType {

    INFORMATIONAL("INFORMATIONAL"),
    SUCCESS("SUCCESS"),
    REDIRECTION("REDIRECTION"),
    CLIENT_ERROR("CLIENT_ERROR"),
    SERVER_ERROR("SERVER_ERROR");

    private String value;

    HttpStatusType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
