package com.user.usercontract.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class FollowingEvent extends Event {
    private static final String name = "FollowingEvent";

    public FollowingEvent() {
        super(name);
    }

    private String followerId;
    private String followeeId;
    private boolean isFollowingACelebrity;
}
