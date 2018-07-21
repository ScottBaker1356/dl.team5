
package com.team5.dl.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "location",
    "eventCode",
    "minutesBeforeNotificationIssued",
    "notificationChannels"
})
public class SensorEventPreference {

    @JsonProperty("location")
    private String location;
    @JsonProperty("eventCode")
    private EventCode eventCode;
    @JsonProperty("minutesBeforeNotificationIssued")
    private Double minutesBeforeNotificationIssued;
    @JsonProperty("notificationChannels")
    private List<NotificationChannel> notificationChannels = new ArrayList<NotificationChannel>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("eventCode")
    public EventCode getEventCode() {
        return eventCode;
    }

    @JsonProperty("eventCode")
    public void setEventCode(EventCode eventCode) {
        this.eventCode = eventCode;
    }

    @JsonProperty("minutesBeforeNotificationIssued")
    public Double getMinutesBeforeNotificationIssued() {
        return minutesBeforeNotificationIssued;
    }

    @JsonProperty("minutesBeforeNotificationIssued")
    public void setMinutesBeforeNotificationIssued(Double minutesBeforeNotificationIssued) {
        this.minutesBeforeNotificationIssued = minutesBeforeNotificationIssued;
    }

    @JsonProperty("notificationChannels")
    public List<NotificationChannel> getNotificationChannels() {
        return notificationChannels;
    }

    @JsonProperty("notificationChannels")
    public void setNotificationChannels(List<NotificationChannel> notificationChannels) {
        this.notificationChannels = notificationChannels;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("location", location).append("eventCode", eventCode).append("minutesBeforeNotificationIssued", minutesBeforeNotificationIssued).append("notificationChannels", notificationChannels).append("additionalProperties", additionalProperties).toString();
    }

    public enum EventCode {

        WATER_PRESENT_NOTIFICATION("WaterPresentNotification"),
        TEMPERATURE_UPDATE_NOTIFICATION("TemperatureUpdateNotification");
        private final String value;
        private final static Map<String, EventCode> CONSTANTS = new HashMap<String, EventCode>();

        static {
            for (EventCode c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private EventCode(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static EventCode fromValue(String value) {
            EventCode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
