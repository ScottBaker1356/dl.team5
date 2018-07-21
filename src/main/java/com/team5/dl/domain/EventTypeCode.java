package com.team5.dl.domain;

public enum EventTypeCode {
    WATER_PRESENT_NOTIFICATION("WaterPresentNotification"),
    TEMPERATURE_UPDATE_NOTIFICATION("TemperatureUpdateNotificaiton");

    private String eventName;

    EventTypeCode(String eventName) {
        this.eventName = eventName;
    }

    public static EventTypeCode codeFor(String eventName) {
        for (EventTypeCode code : EventTypeCode.values()) {
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
