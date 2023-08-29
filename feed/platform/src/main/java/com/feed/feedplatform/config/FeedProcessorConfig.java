package com.feed.feedplatform.config;

import com.feed.feedplatform.processor.CachedFeedProcessorImpl;
import com.feed.feedplatform.processor.CelebFeedProcessorImpl;
import com.feed.feedplatform.processor.IFeedProcessor;
import com.feed.feedplatform.processor.InactiveFeedProcessorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FeedProcessorConfig {

    @Autowired
    private CachedFeedProcessorImpl cachedFeedProcessor;

    @Autowired
    private CelebFeedProcessorImpl celebFeedProcessor;

    @Autowired
    private InactiveFeedProcessorImpl inactiveFeedProcessor;

    @Bean("ActiveUsers")
    public List<IFeedProcessor> activeFeedProcessors() {
        List<IFeedProcessor> feedProcessors = new ArrayList<>();
        feedProcessors.add(cachedFeedProcessor);
        feedProcessors.add(celebFeedProcessor);
        return feedProcessors;
    }

    @Bean("InactiveUsers")
    public List<IFeedProcessor> inactiveFeedProcessors() {
        List<IFeedProcessor> feedProcessors = new ArrayList<>();
        feedProcessors.add(inactiveFeedProcessor);
        feedProcessors.add(celebFeedProcessor);
        return feedProcessors;
    }
}
