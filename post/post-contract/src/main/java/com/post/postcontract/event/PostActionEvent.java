package com.post.postcontract.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class PostActionEvent extends Event {

    private static final String name = "PostActionEvent";

    public PostActionEvent() {
        super(name);
    }

    private String postId;
    private String followerId;
    private String followeeId;
    private String action;
}
