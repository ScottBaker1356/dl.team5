package com.team5.dl;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;

import java.util.HashMap;
import java.util.Map;

public class MockerBuildingUtils {

    public static Exchange buildMockExchange() {
        return buildMockExchange(new HashMap<String, Object>(), "");
    }

    public static Exchange buildMockExchange(Map<String, Object> headers) {
        return buildMockExchange(headers, "");
    }

    public static Exchange buildMockExchange(Object body) {
        return buildMockExchange(new HashMap<String, Object>(), body);
    }

    public static Exchange buildMockExchange(Map<String, Object> additionalHeaders, Object body) {

        Endpoint endpoint = new DefaultEndpoint() {
            @Override
            public Producer createProducer() throws Exception {
                return null;
            }

            @Override
            public Consumer createConsumer(Processor processor) throws Exception {
                return null;
            }

            @Override
            public boolean isSingleton() {
                return false;
            }
        };

        Exchange exchange = new DefaultExchange(endpoint);

        Message message = new DefaultMessage();
        message.getHeaders().putAll(additionalHeaders);
        message.setBody(body);

        exchange.setIn(message);

        return exchange;
    }

}
