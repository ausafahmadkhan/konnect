package com.feed.feedplatform.processor;

import com.feed.feedcontract.response.UserFeedResponse;
import com.feed.feedplatform.utility.CommonUtils;
import com.feed.feedplatform.utility.Constant;
import com.konnect.postservice.client.IPostServiceClient;
import com.konnect.postservice.client.IRankingServiceClient;
import com.post.postcontract.request.GetPostsByUserIdsRequest;
import com.post.postcontract.response.PostIdResponse;
import com.ranking.rankingcontract.response.BaseResponse;
import com.ranking.rankingcontract.response.LastInteractedFollowees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class InactiveFeedProcessorImpl implements IFeedProcessor {

    @Autowired
    private IRankingServiceClient rankingServiceClient;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private IPostServiceClient postServiceClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void process(UserFeedResponse userFeedResponse) {
        BaseResponse<LastInteractedFollowees> baseResponse = rankingServiceClient.getLastInteractedFollowees(userFeedResponse.getUserId());
        if (null != baseResponse && baseResponse.isSuccess()) {
            LastInteractedFollowees lastInteractedFollowees = baseResponse.getData();
            GetPostsByUserIdsRequest getPostsByUserIdsRequest = new GetPostsByUserIdsRequest();
            getPostsByUserIdsRequest.setUserIds(lastInteractedFollowees.getUserIds());
            com.post.postcontract.response.BaseResponse<PostIdResponse> postBaseResponse = postServiceClient.getPostsByUserIds(getPostsByUserIdsRequest);
            if (null != postBaseResponse && postBaseResponse.isSuccess()) {
                PostIdResponse postResponses = postBaseResponse.getData();
                cacheFeed(postResponses.getPostIds(), userFeedResponse);
                commonUtils.updateFeedResponse(postResponses.getPostIds(), userFeedResponse);
            }
        }
    }

    private void cacheFeed(List<String> postIds, UserFeedResponse userFeedResponse) {
        CompletableFuture.runAsync(() -> {
            redisTemplate.opsForList().leftPushAll(String.join(":", Constant.RedisKey.FEED, userFeedResponse.getUserId()), postIds);
        });
    }
}
