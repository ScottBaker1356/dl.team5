package com.team5.dl;

import com.infarmbureau.es.commons.http.client.HttpMethod;
import com.infarmbureau.es.commons.http.client.HttpRequest;
import com.infarmbureau.es.commons.http.exception.HttpException;

public class ServiceHandlerFactory {

    public ServiceHandler create(HttpRequest httpRequest) {
        if (ServiceMarkers.NOTIFY_OF_EVENT.matches(httpRequest)) {
            return new NotifyOfEventHandler(httpRequest);
        }

        throw new HttpException("", 503);
    }

    enum ServiceMarkers {

        NOTIFY_OF_EVENT(HttpMethod.POST, PathTemplate.NOTIFY_OF_EVENT),
        SEND_SMS(HttpMethod.POST, PathTemplate.SEND_SMS),
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
        SEND_SMS("^/api/domain-layer/send-text-message$"),

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
