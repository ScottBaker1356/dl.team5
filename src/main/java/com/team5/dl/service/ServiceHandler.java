package com.team5.dl.service;

import com.team5.dl.Properties;
import com.team5.dl.httpclient.HttpRequest;
import com.team5.dl.httpclient.HttpResponse;
import com.team5.dl.service.textmessaging.SendTextMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.team5.dl.Properties.*;

public abstract class ServiceHandler {

    protected static final Logger LOGGER = LoggerFactory.getLogger(SendTextMessageHandler.class);

    protected String id;
    protected String karafHost;
    protected String accessKey;
    protected String secretKey;
    protected String serviceEndpoint;
    protected String signingRegion;

    protected HttpRequest httpRequest;

    public ServiceHandler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        id = httpRequest.getOtherProperties().get(ID.property());
        karafHost = httpRequest.getOtherProperties().get(KARAF_HOST.property());
        accessKey = httpRequest.getOtherProperties().get(AWS_ACCESS_KEY.property());
        secretKey = httpRequest.getOtherProperties().get(AWS_SECRET_KEY.property());
        serviceEndpoint = httpRequest.getOtherProperties().get(AWS_SERVICE_ENDPOINT.property());
        signingRegion = httpRequest.getOtherProperties().get(AWS_SIGNING_REGION.property());
    }

    public abstract HttpResponse run();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKarafHost() {
        return karafHost;
    }

    public void setKarafHost(String karafHost) {
        this.karafHost = karafHost;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getServiceEndpoint() {
        return serviceEndpoint;
    }

    public void setServiceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public String getSigningRegion() {
        return signingRegion;
    }

    public void setSigningRegion(String signingRegion) {
        this.signingRegion = signingRegion;
    }

}
