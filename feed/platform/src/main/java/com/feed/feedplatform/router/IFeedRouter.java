package com.feed.feedplatform.router;

import com.feed.feedplatform.service.IFeedService;

public interface IFeedRouter {

    IFeedService getFeedServiceForUser(String userId);
}
