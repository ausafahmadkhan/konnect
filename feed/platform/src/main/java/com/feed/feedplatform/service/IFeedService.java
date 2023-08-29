package com.feed.feedplatform.service;

import com.feed.feedcontract.response.UserFeedResponse;

public interface IFeedService {
    UserFeedResponse getFeed(String userId);
}
