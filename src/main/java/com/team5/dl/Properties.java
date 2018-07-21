package com.team5.dl;

public enum Properties {

    ID("id"),
    KARAF_HOST("karaf.host"),
    AWS_ACCESS_KEY("aws.access.key"),
    AWS_SECRET_KEY("aws.secret.key"),
    AWS_SERVICE_ENDPOINT("aws.service.endpoint"),
    AWS_SIGNING_REGION("aws.signing.region"),

    ;

    private String value;

    Properties(String value) {
        this.value = value;
    }

    public String property() {
        return value;
    }
}
