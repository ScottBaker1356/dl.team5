package com.team5.dl;

import com.infarmbureau.es.commons.domain.utils.JavaJsonSerializationController;
import com.infarmbureau.es.commons.http.client.HttpMethod;
import com.infarmbureau.es.commons.http.client.HttpRequest;
import com.infarmbureau.es.commons.http.client.HttpResponse;
import com.team5.dl.domain.SensorEvent;
import org.apache.camel.Exchange;

public class NotifyOfEventHandler extends ServiceHandler {

    public NotifyOfEventHandler(HttpRequest httpRequest) {
        super(httpRequest);
    }

    public HttpResponse run() {
        String body = httpRequest.getBody().get();

        SensorEvent sensorEvent = JavaJsonSerializationController.deserialize(body, SensorEvent.class);

        // TODO: Lookup rules, take action (e.g., send SMS)

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setBody("{\"result\": \"success\"}");
        httpResponse.setHttpResponseCode(200);
        return httpResponse;
    }
}
