package com.team5.dl;

import com.infarmbureau.es.commons.http.client.HttpMethod;
import com.infarmbureau.es.commons.http.client.HttpRequest;
import com.infarmbureau.es.commons.http.client.HttpResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ServiceProcessor implements Processor {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        LOGGER.debug("Entering " + this.getClass().getName());

        HttpRequest httpRequest = new HttpRequest();
        String method = exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class);
        httpRequest.setMethod(Arrays.asList(HttpMethod.values()).stream().filter(m -> m.name().equals(method)).findFirst().get());
        httpRequest.setPath(exchange.getIn().getHeader(Exchange.HTTP_URI, String.class));
        httpRequest.setBody(exchange.getIn().getBody(String.class));

        HttpResponse httpResponse = new ServiceHandlerFactory().create(httpRequest).run();

        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, httpResponse.getHttpResponseCode());
        exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getOut().setBody(httpResponse.getBody().get());

        LOGGER.debug("Leaving " + this.getClass().getName());

    }

}
