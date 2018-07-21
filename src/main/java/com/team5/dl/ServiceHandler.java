package com.team5.dl;

import com.infarmbureau.es.commons.http.client.HttpRequest;
import com.infarmbureau.es.commons.http.client.HttpResponse;

public abstract class ServiceHandler {

    protected HttpRequest httpRequest;

    public ServiceHandler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public abstract HttpResponse run();
}
