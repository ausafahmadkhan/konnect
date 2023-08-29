package com.feed.feedplatform.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feed.feedcontract.response.RankedUserFeedResponse;
import com.feed.feedcontract.response.UserFeedResponse;
import com.konnect.postservice.client.IPostServiceClient;
import com.konnect.postservice.client.IRankingServiceClient;
import com.post.postcontract.event.PostEvent;
import com.post.postcontract.request.GetPostsByIdRequest;
import com.post.postcontract.response.BaseResponse;
import com.post.postcontract.response.PostResponse;
import com.ranking.rankingcontract.request.RankingRequest;
import com.ranking.rankingcontract.response.RankingResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class CommonUtils {

    private static Logger logger = LogManager.getLogger(CommonUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IPostServiceClient postServiceClient;

    @Autowired
    private IRankingServiceClient rankingServiceClient;


    public void updateFeedResponse(List<String> postIds, UserFeedResponse userFeedResponse) {
        if (null != postIds && !postIds.isEmpty()) {
            Set<PostResponse> postResponseList = new HashSet<>();
            List<String> absentPostIds = new ArrayList<>();
            for (String postId : postIds) {
                try {
                    String post = redisTemplate.opsForValue().get(String.join(":", Constant.RedisKey.POST, postId));
                    if (null == post || post.isEmpty()) {
                        absentPostIds.add(postId);
                    } else {
                        PostEvent postEvent = mapper.readValue(post, PostEvent.class);
                        postResponseList.add(modelMapper.map(postEvent, PostResponse.class));
                    }
                } catch (Exception e) {
                    //swallow
                }
            }
            userFeedResponse.getPosts().addAll(postResponseList);
            Optional<List<PostResponse>> postResponses = getPostDetailsFromPostService(absentPostIds);
            postResponses.ifPresent(responses -> userFeedResponse.getPosts().addAll(responses));
        }
    }

    private Optional<List<PostResponse>> getPostDetailsFromPostService(List<String> postIds) {
        try {
            GetPostsByIdRequest getPostsByIdRequest = new GetPostsByIdRequest();
            getPostsByIdRequest.setPostIds(postIds);
            BaseResponse<List<PostResponse>> baseResponse = postServiceClient.getPostsByIds(getPostsByIdRequest);
            if (null != baseResponse && baseResponse.isSuccess()) {
                List<PostResponse> postResponses = baseResponse.getData();
                CompletableFuture.runAsync(() -> {
                    for (PostResponse postResponse : postResponses) {
                        cachePost(postResponse);
                    }
                });

                return Optional.of(postResponses);
            }
        }
        catch (Exception e) {
            logger.error("Could not call post service : {}", e.getMessage());
        }
        return Optional.empty();
    }

    private void cachePost(PostResponse postResponse) {
        PostEvent post = modelMapper.map(postResponse, PostEvent.class);
        try {
            redisTemplate.opsForValue().set(String.join(":", Constant.RedisKey.POST, post.getId()), mapper.writeValueAsString(post), 24, TimeUnit.HOURS);
        } catch (JsonProcessingException e) {
            //swallow
        }
    }

    public UserFeedResponse rankedFeed(UserFeedResponse userFeedResponse) {
        RankingRequest rankingRequest = new RankingRequest();
        rankingRequest.setAffinityOf(userFeedResponse.getUserId());
        List<String> followees = userFeedResponse.getPosts()
                .stream()
                .map(PostResponse::getUserId)
                .collect(Collectors.toList());
        rankingRequest.setUserIds(followees);

        try {
            com.ranking.rankingcontract.response.BaseResponse<RankingResponse> baseResponse = rankingServiceClient.rankFollowees(rankingRequest);
            if (null != baseResponse && baseResponse.isSuccess()) {
                RankingResponse rankingResponse = baseResponse.getData();
                Map<String, Integer> affinityMap = rankingResponse.getAffinityMap();
                Comparator<PostResponse> postResponseComparator = (a, b) -> {
                    if (!a.getUserId().equals(b.getUserId())) {
                        return affinityMap.get(b.getUserId()).compareTo(affinityMap.get(a.getUserId()));
                    }
                    if (a.getTotalLikes() != b.getTotalLikes()) {
                        return (int) (b.getTotalLikes() - a.getTotalLikes());
                    }
                    if (a.getTotalComments() != b.getTotalComments()) {
                        return (int) (b.getTotalComments() - a.getTotalComments());
                    }if (a.getTotalShares() != b.getTotalShares()) {
                        return (int) (b.getTotalShares() - a.getTotalShares());
                    }
                    return 0;
                };
                RankedUserFeedResponse rankedUserFeedResponse = new RankedUserFeedResponse();
                List<PostResponse> postResponses = new ArrayList<>(userFeedResponse.getPosts());
                postResponses.sort(postResponseComparator);
                rankedUserFeedResponse.setUserId(userFeedResponse.getUserId());
                rankedUserFeedResponse.setRankedPosts(postResponses);
                return rankedUserFeedResponse;
            }
        }
        catch (Exception e)
        {
            logger.error("Could not rank feed : {}", e.getMessage());
        }
        return userFeedResponse;
    }
}
