package com.post.postcontract.event;

import com.post.postcontract.enums.PostType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class PostEvent extends Event {
    private static final String name = "PostEvent";

    public PostEvent() {
        super(name);
    }

    private String id;
    private PostType postType;
    private String userId;
    private String text;
    private String pincode;
    private String imageUrl;
    private boolean isCelebPost;
    private long totalLikes;
    private long totalComments;
    private long totalShares;
    private Long updatedAt;
}
