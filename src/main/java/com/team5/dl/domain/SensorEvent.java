package com.team5.dl.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "sensorId",
        "eventTypeCode",
        "measuredValue"
})
public class SensorEvent {
    @JsonProperty("sensorId")
    private String sensorId;
    @JsonProperty("eventTypeCode")
    private String eventTypeCode;
    @JsonProperty("measuredValue")
    private Double measuredValue;

    @JsonProperty("sensorId")
    public String getSensorId() {
        return sensorId;
    }
    @JsonProperty("sensorId")
    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
    @JsonProperty("eventTypeCode")
    public String getEventTypeCode() {
        return eventTypeCode;
    }
    @JsonProperty("eventTypeCode")
    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }
    @JsonProperty("measuredValue")
    public Double getMeasuredValue() {
        return measuredValue;
    }
    @JsonProperty("measuredValue")
    public void setMeasuredValue(Double measuredValue) {
        this.measuredValue = measuredValue;
    }
}