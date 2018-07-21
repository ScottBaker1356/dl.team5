package com.team5.dl.httpclient;

public enum HttpMethod {
    GET(false),
    POST(true),
    PUT(true),
    DELETE(false);

    private boolean output;

    private HttpMethod(boolean output) {
        this.output = output;
    }

    public boolean hasOutput() {
        return this.output;
    }
}
