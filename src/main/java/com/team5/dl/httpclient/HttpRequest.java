package com.team5.dl.httpclient;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {

    private HttpMethod method;
    private String hostname;
    private String path;
    private Map<String, String> headers = new LinkedHashMap<>();
    private Map<String, String> queryParameters = new LinkedHashMap<>();
    private Optional<String> body = Optional.empty();
    private Map<String, String> otherProperties = new LinkedHashMap<>();

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public Optional<String> getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = Optional.of(body);
    }

    public Map<String, String> getOtherProperties() {
        return otherProperties;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Method: " + (null == this.method ? "(null)" : this.method.name()) + "\n";
        s += "Hostname: " + (null == this.hostname ? "(null)" : this.hostname) + "\n";
        s += "Path: " + (null == this.path ? "(null)" : this.path) + "\n";
        s += buildNoteOnCollection(headers, "Header");
        s += buildNoteOnCollection(queryParameters, "Query Parameter");
        s += "Body: " + (!this.body.isPresent() ? "(null)" : this.body.get()) + "\n";
        s += buildNoteOnCollection(otherProperties, "Other Properties");
        return s;
    }

    private String buildNoteOnCollection(Map<String, String> map, String context) {
        Collection<String> keySet = map.keySet();
        String collectionNote = "";
        for(String key : keySet) {
            String value = headers.get(key);
            collectionNote += context + " (key, property): " + key + ", " + (null == value ? "(null)" : value) + "\n";
        }
        return collectionNote;
    }

}
