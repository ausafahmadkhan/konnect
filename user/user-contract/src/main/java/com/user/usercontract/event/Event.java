package com.user.usercontract.event;

import lombok.Data;

@Data
public class Event {
    protected String eventName;

    public Event(String eventName) {
        this.eventName = eventName;
    }
}
