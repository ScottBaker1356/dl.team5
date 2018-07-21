package com.team5.dl;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceProcessor implements Processor {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        LOGGER.debug("Entering " + this.getClass().getName());

        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getOut().setBody("{\"key\":\"Hello World!\"}");

        LOGGER.debug("Leaving " + this.getClass().getName());

    }

}
