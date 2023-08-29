package com.post.postcontract.response;

import com.post.postcontract.enums.PostType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostResponse {
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
}