package com.feed.feedcontract.response;

import com.post.postcontract.response.PostResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class RankedUserFeedResponse extends UserFeedResponse {
    private List<PostResponse> rankedPosts;
}
