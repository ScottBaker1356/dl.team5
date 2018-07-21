package com.team5.dl.httpclient;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class HttpResponse {

    private Integer httpResponseCode;
    private Map<String, String> headers = new LinkedHashMap<>();
    private String message;
    private Optional<String> body = Optional.empty();

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Optional<String> getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = Optional.of(body);
    }

    @Override
    public String toString() {
        String s = "";
        s += "Response Code: " + (null == this.httpResponseCode ? "(null)" : this.httpResponseCode.toString()) + "\n";
        s += "Message: " + (null == this.message ? "(null)" : this.message) + "\n";
        s += "Body: " + (!this.body.isPresent() ? "(null)" : this.body.get()) + "\n";
        return s;
    }

}
