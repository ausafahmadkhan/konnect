package com.feed.feedcontract.response;

import com.post.postcontract.response.PostResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserFeedResponse {
    private String userId;
    private Set<PostResponse> posts;
}
