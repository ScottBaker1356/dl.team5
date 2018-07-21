package com.team5.dl.httpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public abstract class HttpClient implements Callable<HttpResponse> {

    protected static Logger logger = LoggerFactory.getLogger(HttpClient.class);

    protected HttpRequest request;

    public HttpClient(HttpRequest request) {
        this.request = request;
    }

    public abstract HttpResponse call();

}
