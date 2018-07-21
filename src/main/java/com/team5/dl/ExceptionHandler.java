package com.team5.dl;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionHandler implements Processor {
    private static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);
    private Logger logger;

    public ExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    public ExceptionHandler() {
        this(LOGGER);
    }

    public void process(Exchange exchange) throws Exception {
        String routeId = exchange.getUnitOfWork().getRouteContext().getRoute().getId();
        this.logger.error("--------------------");
        this.logger.error("Processing exception in route " + routeId);
        this.logger.error("--------------------");
        Exception e = (Exception)exchange.getProperty("CamelExceptionCaught", Exception.class);
        if (this.logger.isDebugEnabled()) {
            StackTraceElement[] stes = e.getStackTrace();
            if (stes != null && stes.length > -1) {
                this.logger.debug("Stack trace:");

                for(int i = 0; i < stes.length; ++i) {
                    StackTraceElement ste = stes[i];
                    this.logger.debug(ste.getClassName() + " " + ste.getFileName() + " " + ste.getMethodName() + " " + ste.getLineNumber());
                }
            }
        }

        if (exchange.getIn().getBody() != null) {
            this.logger.error("In message: " + (String)exchange.getIn().getBody(String.class));
        }

        if (exchange.getOut().getBody() != null) {
            this.logger.error("Out message: " + (String)exchange.getOut().getBody(String.class));
        }

        this.logger.error("getMessage Exception: " + e.getMessage() + " occurred");
        this.logger.error("getLocalized Message Exception: " + e.getLocalizedMessage() + " occurred");
        this.logger.error("getName Exception: " + e.getClass().getName() + " occurred");
        this.logger.error("getSimpleName Exception: " + e.getClass().getSimpleName() + " occurred");
        this.logger.error("getCanonicalName Exception: " + e.getClass().getCanonicalName() + " occurred");
        this.logger.debug("Exception type/name: " + e.getLocalizedMessage() + " " + e.getMessage() + " " + e.getClass().getName());
        exchange.getOut().setHeader("CamelHttpResponseCode", Integer.valueOf(500));
        exchange.getOut().setHeader("Content-Type", "application/json");
        exchange.getOut().setBody(this.buildExceptionBody());
    }

    private String buildExceptionBody() {
        return "{\"data\" : [ \"Server Error\" ] }";
    }
}
