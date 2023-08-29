package com.feed.feedplatform.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feed.feedcontract.response.UserFeedResponse;
import com.feed.feedplatform.utility.CommonUtils;
import com.feed.feedplatform.utility.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CachedFeedProcessorImpl implements IFeedProcessor {

    private static Logger logger = LogManager.getLogger(CachedFeedProcessorImpl.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CommonUtils commonUtils;

    @Override
    public void process(UserFeedResponse userFeedResponse) {
        String userId = userFeedResponse.getUserId();
        List<String> postIds = redisTemplate.opsForList().range(String.join(":", Constant.RedisKey.FEED, userId), 0, 50);
        commonUtils.updateFeedResponse(postIds, userFeedResponse);
    }
}
