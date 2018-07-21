package com.team5.dl;

public enum EventType {

    WATER_PRESENT_NOTIFICATION("WaterPresentNotification"),
    TEMPURATURE_UPDATE_NOTIFICATION("TemperatureUpdateNotification"),

    ;

    private String typeCode;

    EventType(String typeCode) {
        this.typeCode = typeCode;
    }

    public String type() {
        return typeCode;
    }

    public boolean matches(String eventTypeCode) {
        return typeCode.equals(eventTypeCode);
    }

}
