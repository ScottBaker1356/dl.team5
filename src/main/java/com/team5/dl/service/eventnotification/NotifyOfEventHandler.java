package com.team5.dl.service.eventnotification;

import com.team5.dl.JavaJsonSerializationController;
import com.team5.dl.domain.*;
import com.team5.dl.service.ServiceHandler;
import com.team5.dl.httpclient.*;
import com.team5.dl.service.eventpreferences.SensorEventPreferencesRetrievalHandler;
import com.team5.dl.service.textmessaging.SendTextMessageHandler;

import java.util.List;
import java.util.Optional;

public class NotifyOfEventHandler extends ServiceHandler {

    private static final String WATER_LIQUID = "Water detected on floor in %s!";
    private static final String WATER_SOLID = "Pipes at risk of freezing! Temperature of water in pipes in %s currently at %sº Fahrenheit.";

    public NotifyOfEventHandler(HttpRequest httpRequest) {
        super(httpRequest);
    }

    public HttpResponse run() {
        String notifyOfEventBody = httpRequest.getBody().get();

        SensorEvent sensorEvent = JavaJsonSerializationController.deserialize(notifyOfEventBody, SensorEvent.class);

        HttpResponse sensorEventPreferencesResponse = SensorEventPreferencesRetrievalHandler.retrieveCustomers();
        String sensorEventPreferencesResponseJson = sensorEventPreferencesResponse.getBody().get();
        Customers customers = JavaJsonSerializationController.deserialize(sensorEventPreferencesResponseJson, Customers.class);
        Customer customer = customers.getCustomers().get(0);
        SensorEventPreference sensorEventPreference = customer.getSensorEventPreferences().stream().filter(p -> sensorEvent.getEventTypeCode().equals(p.getEventCode().value())).findFirst().get();
        Optional<NotificationChannel> channel = sensorEventPreference.getNotificationChannels().stream().filter(n -> "TextMessage".equals(n.getChannelType())).findFirst();

        if(channel.isPresent() && channel.get().getEnabled()) {

            List<String> contacts = channel.get().getContacts();
            String message = buildMessage(sensorEventPreference, sensorEvent);

            HttpRequest sendTextMessageRequest = new HttpRequest();
            sendTextMessageRequest.setHostname(karafHost);
            sendTextMessageRequest.setPath("/api/domain-layer/send-text-message");
            TextMessage textMessage = new TextMessage();
            textMessage.getPhoneNumbers().addAll(contacts);
            textMessage.setMessage(message);
            String sendTextMessageBody = JavaJsonSerializationController.serialize(textMessage);
            sendTextMessageRequest.setBody(sendTextMessageBody);

//            HttpClient sendTextMessageClient = new GenericHttpClient(sendTextMessageRequest);
//            sendTextMessageClient.call();

            SendTextMessageHandler sendTextMessageHandler = new SendTextMessageHandler(sendTextMessageRequest);
            sendTextMessageHandler.setId(id);
            sendTextMessageHandler.setKarafHost(karafHost);
            sendTextMessageHandler.setAccessKey(accessKey);
            sendTextMessageHandler.setSecretKey(secretKey);
            sendTextMessageHandler.setServiceEndpoint(serviceEndpoint);
            sendTextMessageHandler.setSigningRegion(signingRegion);
            sendTextMessageHandler.run();
        }



        return getHttpResponse();
    }

    private String buildMessage(SensorEventPreference preference, SensorEvent sensorEvent) {
        String message = null;
        if(SensorEventPreference.EventCode.WATER_PRESENT_NOTIFICATION.equals(preference.getEventCode())) {
            message = String.format(WATER_LIQUID, preference.getLocation());
        }
        else if(SensorEventPreference.EventCode.TEMPERATURE_UPDATE_NOTIFICATION.equals(preference.getEventCode())) {
            message = String.format(WATER_SOLID, preference.getLocation(), sensorEvent.getMeasuredValue());
        }
        return message;
    }

    private HttpResponse getHttpResponse() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHttpResponseCode(200);
        httpResponse.setBody("{\"sayFriendAndEnter\":\"Friend\"}");
        httpResponse.getHeaders().put("Content-Type", "application/json");
        return httpResponse;
    }
}
