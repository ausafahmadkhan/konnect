package com.feed.feedplatform.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feed.feedplatform.utility.Constant;
import com.konnect.kafkaconsumer.listener.ITopicListener;
import com.post.postcontract.event.PostEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component("Post")
public class PostListener implements ITopicListener {

    Logger logger = LogManager.getLogger(PostListener.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void consume(ConsumerRecord<String, String> payload) {
        if (!payload.topic().equals(Constant.KafkaTopic.POST)) {
            return;
        }
        logger.info("Consuming Post Event : " + payload);
        try {
            PostEvent postEvent = objectMapper.readValue(payload.value(), PostEvent.class);
            redisTemplate.opsForValue().set(String.join(":", Constant.RedisKey.POST, postEvent.getId()), payload.value(), 24, TimeUnit.HOURS);
            updateUserFeed(postEvent);
        } catch (Exception e) {
            logger.error("Could not process event : {}", e.getMessage());
        }
    }

    private void updateUserFeed(PostEvent postEvent) {
        //not maintaining follow list of celeb in cache
        if (postEvent.isCelebPost()) {
            return;
        }

        List<String> totalFollowers = redisTemplate.opsForList().range(String.join(":", Constant.RedisKey.FOLLOWING, postEvent.getUserId()), 0, -1);
        if (null == totalFollowers || totalFollowers.isEmpty()) {
            return;
        }

        totalFollowers
                .forEach(follower -> {
                    redisTemplate.opsForList().leftPush(String.join(":", Constant.RedisKey.FEED, follower), postEvent.getId());
                    trimFeedListAsync(follower);
                });
    }

    private void trimFeedListAsync(String follower) {
        CompletableFuture.runAsync(() ->
        {
            try {
                Long totalFeeds = redisTemplate.opsForList().size(String.join(":", Constant.RedisKey.FEED, follower));
                if (null != totalFeeds && totalFeeds > 1000) {
                    redisTemplate.opsForList().trim(String.join(":", Constant.RedisKey.FEED, follower), 0, 1000);
                }
            }
            catch (Exception e) {
                logger.error("Could not trim the feed list : {}", e.getMessage());
            }
        });
    }
}
