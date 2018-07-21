package com.team5.dl.service.eventnotification;

import com.team5.dl.JavaJsonSerializationController;
import com.team5.dl.service.ServiceHandler;
import com.team5.dl.domain.SensorEvent;
import com.team5.dl.httpclient.*;

public class NotifyOfEventHandler extends ServiceHandler {

    public NotifyOfEventHandler(HttpRequest httpRequest) {
        super(httpRequest);
    }

    public HttpResponse run() {
        String body = httpRequest.getBody().get();

        SensorEvent sensorEvent = JavaJsonSerializationController.deserialize(body, SensorEvent.class);

        // TODO: Lookup rules, take action (e.g., send SMS)

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHttpResponseCode(200);
        return httpResponse;
    }
}
