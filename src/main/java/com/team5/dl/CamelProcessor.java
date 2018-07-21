package com.team5.dl;

import com.team5.dl.httpclient.*;
import com.team5.dl.service.ServiceHandlerFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static com.team5.dl.Properties.*;

public class CamelProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelProcessor.class);
    private static final ServiceHandlerFactory serviceHandlerFactory = new ServiceHandlerFactory();

    @Override
    public void process(Exchange exchange) throws Exception {

        LOGGER.debug("Entering " + this.getClass().getName());

        HttpRequest httpRequest = new HttpRequest();
        String method = exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class);
        httpRequest.setMethod(Arrays.asList(HttpMethod.values()).stream().filter(m -> m.name().equals(method)).findFirst().get());
        httpRequest.setPath(exchange.getIn().getHeader(Exchange.HTTP_URI, String.class));
        httpRequest.setBody(exchange.getIn().getBody(String.class));
        httpRequest.getOtherProperties().put(ID.property(), exchange.getProperty(ID.property(), String.class));
        httpRequest.getOtherProperties().put(KARAF_HOST.property(), exchange.getProperty(KARAF_HOST.property(), String.class));
        httpRequest.getOtherProperties().put(AWS_ACCESS_KEY.property(), exchange.getProperty(AWS_ACCESS_KEY.property(), String.class));
        httpRequest.getOtherProperties().put(AWS_SECRET_KEY.property(), exchange.getProperty(AWS_SECRET_KEY.property(), String.class));
        httpRequest.getOtherProperties().put(AWS_SERVICE_ENDPOINT.property(), exchange.getProperty(AWS_SERVICE_ENDPOINT.property(), String.class));
        httpRequest.getOtherProperties().put(AWS_SIGNING_REGION.property(), exchange.getProperty(AWS_SIGNING_REGION.property(), String.class));

        HttpResponse httpResponse = serviceHandlerFactory.create(httpRequest).run();

        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, httpResponse.getHttpResponseCode());
        if(httpResponse.getBody().isPresent()) {
            exchange.getOut().setHeader(Exchange.CONTENT_TYPE, httpResponse.getHeaders().get(Exchange.CONTENT_TYPE));
            exchange.getOut().setBody(httpResponse.getBody().get());
        }

        LOGGER.debug("Leaving " + this.getClass().getName());

    }

}
