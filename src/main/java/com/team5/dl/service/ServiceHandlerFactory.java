package com.team5.dl.service;

import com.team5.dl.service.eventnotification.NotifyOfEventHandler;
import com.team5.dl.httpclient.*;
import com.team5.dl.service.textmessaging.SendTextMessageHandler;

public class ServiceHandlerFactory {

    public ServiceHandler create(HttpRequest httpRequest) {

        if (ServiceMarkers.NOTIFY_OF_EVENT.matches(httpRequest)) {
            return new NotifyOfEventHandler(httpRequest);
        }

        else if (ServiceMarkers.SEND_TEXT_MESSAGE.matches(httpRequest)) {
            return new SendTextMessageHandler(httpRequest);
        }

        throw new HttpException("", 503);
    }

    enum ServiceMarkers {

        NOTIFY_OF_EVENT(HttpMethod.POST, PathTemplate.NOTIFY_OF_EVENT),
        SEND_TEXT_MESSAGE(HttpMethod.POST, PathTemplate.SEND_TEXT_MESSAGE),
        SENSOR_EVENT_PREFERENCES_RETRIEVAL(HttpMethod.GET, PathTemplate.SENSOR_EVENT_PREFERENCES),
        ;

        private HttpMethod method;
        private String template;

        ServiceMarkers(HttpMethod method, PathTemplate template) {
            this.method = method;
            this.template = template.template();
        }

        public boolean matches(HttpRequest httpRequest) {
            return null != httpRequest
                    && this.method.equals(httpRequest.getMethod())
                    && null != httpRequest.getPath()
                    && httpRequest.getPath().matches(template);
        }

    }

    enum PathTemplate {

        NOTIFY_OF_EVENT("^/api/domain-layer/notify-of-event$"),
        SEND_TEXT_MESSAGE("^/api/domain-layer/send-text-message$"),
        SENSOR_EVENT_PREFERENCES("^/api/domain-layer/send-text-message/[a-zA-Z0-9]*$"),

        ;

        private String template;

        PathTemplate(String template) {
            this.template = template;
        }

        public String template() {
            return template;
        }
    }
}
