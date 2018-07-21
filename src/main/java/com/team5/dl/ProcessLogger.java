package com.team5.dl;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessLogger implements Processor {
	private static Logger LOGGER = LoggerFactory.getLogger(ProcessLogger.class);
	private Logger logger;

	public ProcessLogger(Logger logger) {
		this.logger = logger;
	}

	public ProcessLogger() {
		this(LOGGER);
	}

	public void process(Exchange exchange) throws Exception {
		String routeId = exchange.getUnitOfWork().getRouteContext().getRoute().getId();

		try {
			String inHeaders = exchange.getIn().getHeaders().toString();
			String inBody = (String)exchange.getIn().getBody(String.class);
			this.logger.info(this.logger.getName() + ": ###  In route " + routeId);
			this.logger.debug(this.logger.getName() + ":  **** Route Id = " + routeId);
			this.logger.debug(this.logger.getName() + ":  In Headers = " + inHeaders);
			this.logger.debug(this.logger.getName() + ":  In Body = " + inBody);
		} catch (Exception var5) {
			this.logger.error(this.logger.getName() + ": Exception happen when process in route " + routeId);
			throw var5;
		}
	}
}
