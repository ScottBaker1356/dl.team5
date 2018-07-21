package com.team5.dl.domain;

public enum SensorEventTypeCode {
    WATER_PRESENT_NOTIFICATION("WaterPresentNotification"),
    TEMPERATURE_UPDATE_NOTIFICATION("TemperatureUpdateNotificaiton");

    private String eventName;

    SensorEventTypeCode(String eventName) {
        this.eventName = eventName;
    }

    public static SensorEventTypeCode codeFor(String eventName) {
        for (SensorEventTypeCode code : SensorEventTypeCode.values()) {
            if (code.getEventName().equals(eventName)) {
                return code;
            }
        }
        return null;
    }

    public String getEventName() {
        return eventName;
    }
}
