package com.post.postcontract.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Event {
    protected String eventName;

    protected Event(String eventName) {
        this.eventName = eventName;
    }
}
