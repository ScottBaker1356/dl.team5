package com.team5.dl;

import org.apache.camel.Exchange;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class SMSServiceProcessorTest {

    @Ignore
    @Test
    public void test() throws Exception {

        String body = "" +
                "{\n" +
                "  \"phoneNumbers\":[\"+13179194341\"],\n" +
                "  \"message\": \"This is a test\"\n" +
                "}";
        Exchange exchange = MockerBuildingUtils.buildMockExchange(body);

        SMSServiceProcessor sut = new SMSServiceProcessor();

        //sut.process(exchange);

        int responseStatusCode = exchange.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);

        assertEquals(200, responseStatusCode);
    }

}
