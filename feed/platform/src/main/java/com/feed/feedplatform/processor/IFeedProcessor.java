package com.feed.feedplatform.processor;

import com.feed.feedcontract.response.UserFeedResponse;

public interface IFeedProcessor {
    void process(UserFeedResponse userFeedResponse);
}
