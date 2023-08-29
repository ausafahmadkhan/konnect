package com.feed.feedplatform.router;

import com.feed.feedplatform.service.IFeedService;
import com.feed.feedplatform.utility.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class IFeedRouterImpl implements IFeedRouter {

    @Autowired
    @Qualifier("ActiveUsersService")
    private IFeedService activeFeedService;

    @Autowired
    @Qualifier("InactiveUsersService")
    private IFeedService inactiveFeedService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public IFeedService getFeedServiceForUser(String userId) {
        boolean feedExists = Boolean.TRUE.equals(redisTemplate.hasKey(String.join(":", Constant.RedisKey.FEED, userId)));
        if (feedExists) {
            return activeFeedService;
        }
        else {
            return inactiveFeedService;
        }
    }
}
