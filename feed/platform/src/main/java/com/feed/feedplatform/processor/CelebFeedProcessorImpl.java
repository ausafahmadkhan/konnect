package com.feed.feedplatform.processor;

import com.feed.feedcontract.response.UserFeedResponse;
import com.feed.feedplatform.utility.CommonUtils;
import com.konnect.postservice.client.IPostServiceClient;
import com.konnect.userservice.client.IUserServiceClient;
import com.post.postcontract.request.GetPostsByUserIdsRequest;
import com.post.postcontract.response.PostIdResponse;
import com.user.usercontract.response.BaseResponse;
import com.user.usercontract.response.CelebrityResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CelebFeedProcessorImpl implements IFeedProcessor {

    Logger logger = LogManager.getLogger(CelebFeedProcessorImpl.class);
    @Autowired
    private IPostServiceClient postServiceClient;

    @Autowired
    private IUserServiceClient userServiceClient;

    @Autowired
    private CommonUtils commonUtils;
    @Override
    public void process(UserFeedResponse userFeedResponse) {
        Optional<List<String>> celebrityFollowing = getAllCelebFollowing(userFeedResponse.getUserId());
        if (celebrityFollowing.isEmpty()) {
            return;
        }

        GetPostsByUserIdsRequest getPostsByUserIdsRequest = new GetPostsByUserIdsRequest();
        getPostsByUserIdsRequest.setUserIds(celebrityFollowing.get());
        com.post.postcontract.response.BaseResponse<PostIdResponse> baseResponse = postServiceClient.getPostsByUserIds(getPostsByUserIdsRequest);
        if (null != baseResponse && baseResponse.isSuccess()) {
            commonUtils.updateFeedResponse(baseResponse.getData().getPostIds(), userFeedResponse);
        }
    }

    private Optional<List<String>> getAllCelebFollowing(String userId) {
        try {
            BaseResponse<CelebrityResponse> baseResponse = userServiceClient.getAllCelebrityFollowings(userId);
            if (null != baseResponse && baseResponse.isSuccess()) {
                return Optional.of(baseResponse.getData().getCelebrityIds());
            }
        }
        catch(Exception e){
                logger.error("Could not call user service : {}", e.getMessage());
        }
        return Optional.empty();
    }
}
