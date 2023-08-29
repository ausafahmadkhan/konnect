package com.feed.feedplatform.service;

import com.feed.feedcontract.response.UserFeedResponse;
import com.feed.feedplatform.processor.IFeedProcessor;
import com.feed.feedplatform.utility.CommonUtils;
import com.konnect.postservice.client.IRankingServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service("ActiveUsersService")
public class ActiveUserFeedServiceImpl implements IFeedService {

    private static Logger logger = LogManager.getLogger(ActiveUserFeedServiceImpl.class);
    @Autowired
    @Qualifier("ActiveUsers")
    private List<IFeedProcessor> feedProcessors;

    @Autowired
    private IRankingServiceClient rankingServiceClient;

    @Autowired
    private CommonUtils commonUtils;

    @Override
    public UserFeedResponse getFeed(String userId) {

        UserFeedResponse userFeedResponse = new UserFeedResponse();
        userFeedResponse.setUserId(userId);
        userFeedResponse.setPosts(new HashSet<>());

        for (IFeedProcessor feedProcessor : feedProcessors) {
            feedProcessor.process(userFeedResponse);
        }

        return commonUtils.rankedFeed(userFeedResponse);
    }
}
