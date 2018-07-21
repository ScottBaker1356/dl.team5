package com.team5.dl;

import aQute.bnd.osgi.Clazz;
import com.team5.dl.domain.Customer;
import com.team5.dl.domain.Customers;
import org.apache.camel.Exchange;
import org.junit.Ignore;
import org.junit.Test;

import static com.team5.dl.MockerBuildingUtils.buildMockExchange;
import static com.team5.dl.Properties.*;
import static org.junit.Assert.*;

@Ignore
public class CamelProcessorTest {

    private void execute(Exchange exchange, CamelProcessor sut) throws Exception {
        //sut.process(exchange);
    }

    @Test
    public void test_SendTextMessage() throws Exception {

        String body = "" +
                "{\n" +
                "  \"phoneNumbers\":[\"+13179194341\"],\n" +
                "  \"message\": \"This is a test by Scott Baker\"\n" +
                "}";
        Exchange exchange = buildMockExchange();
        exchange.getIn().setHeader(Exchange.HTTP_METHOD, "POST");
        exchange.getIn().setHeader(Exchange.HTTP_URI, "/api/domain-layer/send-text-message");
        setProperties(exchange);
        exchange.getIn().setBody(body);

        CamelProcessor sut = new CamelProcessor();

        execute(exchange, sut);

        int responseStatusCode = exchange.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);

        assertEquals(200, responseStatusCode);
    }

    private void setProperties(Exchange exchange) {
        String directoryPath = "C:/ifbidev/GIT-REPOS/dl.team5/security";
        String fileName = "keys.json";
        String json = new FileMocker(directoryPath, fileName).getContents();
        Keys keys = JavaJsonSerializationController.deserialize(json, Keys.class);
        exchange.setProperty(KARAF_HOST.property(), "http://localhost:8181");
        exchange.setProperty(AWS_ACCESS_KEY.property(), keys.getAccessKey());
        exchange.setProperty(AWS_SECRET_KEY.property(), keys.getSecretKey());
        exchange.setProperty(AWS_SERVICE_ENDPOINT.property(), "sns.us-east-1.amazonaws.com");
        exchange.setProperty(AWS_SIGNING_REGION.property(), "us-east-1");
    }

    @Test
    public void test_SensorEventPreferences() throws Exception {

        String customerId = "bfc2983e-e9f9-4585-9f48-94b21fb8cbe5";
        String body = getSensorEventPreferencesRetrievalBody();

        Exchange exchange = buildMockExchange();
        exchange.getIn().setHeader(Exchange.HTTP_METHOD, "GET");
        String uri = "/api/domain-layer/sensor-event-preferences/" + customerId;
        exchange.getIn().setHeader(Exchange.HTTP_URI, uri);
        exchange.getIn().setBody(body);
        setProperties(exchange);
        exchange.setProperty(ID.property(), customerId);

        CamelProcessor sut = new CamelProcessor();

        execute(exchange, sut);

        int responseStatusCode = exchange.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);

        assertEquals(200, responseStatusCode);

        Customers customers = JavaJsonSerializationController.deserialize(exchange.getOut().getBody(String.class), Customers.class);
        assertEquals(customerId, customers.getCustomers().get(0).getCustomerId());
    }

    private String getSensorEventPreferencesRetrievalBody() {
        return "" +
                "{\n" +
                "    \"customers\": [\n" +
                "        {\n" +
                "            \"customerId\": \"bfc2983e-e9f9-4585-9f48-94b21fb8cbe5\",\n" +
                "            \"eventPreferences\": [\n" +
                "                {\n" +
                "                    \"location\": \"Basement\",\n" +
                "                    \"eventCode\": \"WaterPresentNotification\",\n" +
                "                    \"minutesBeforeNotificationIssued\": 120,\n" +
                "                    \"notificationChannels\": [\n" +
                "                        {\n" +
                "                            \"channelType\": \"TextMessage\",\n" +
                "                            \"enabled\": true,\n" +
                "                            \"contacts\": [\"3179194341\"]\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"location\": \"Basement\",\n" +
                "                    \"eventCode\": \"TemperatureUpdateNotification\",\n" +
                "                    \"minutesBeforeNotificationIssued\": 0,\n" +
                "                    \"notificationChannels\": [\n" +
                "                        {\n" +
                "                            \"channelType\": \"TextMessage\",\n" +
                "                            \"enabled\": true,\n" +
                "                            \"contacts\": [\n" +
                "                                \"+13179194341\",\n" +
                "                                \"+15743092414\"\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

}
