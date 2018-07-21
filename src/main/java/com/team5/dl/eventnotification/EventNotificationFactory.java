package com.team5.dl.eventnotification;

import com.team5.dl.EventType;

public class EventNotificationFactory {

    public EventNotification create(String eventTypeCode) {

        if(EventType.WATER_PRESENT_NOTIFICATION.matches(eventTypeCode)) {

        }

        else if(EventType.TEMPURATURE_UPDATE_NOTIFICATION.matches(eventTypeCode)) {

        }

        return null;

    }

}
