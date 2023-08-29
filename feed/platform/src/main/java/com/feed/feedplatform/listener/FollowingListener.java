package com.feed.feedplatform.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feed.feedplatform.utility.Constant;
import com.konnect.kafkaconsumer.listener.ITopicListener;
import com.user.usercontract.event.FollowingEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component("Following")
public class FollowingListener implements ITopicListener {

    Logger logger = LogManager.getLogger(FollowingListener.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void consume(ConsumerRecord<String, String> payload) {
        if (!payload.topic().equals(Constant.KafkaTopic.FOLLOWING)) {
            return;
        }
        logger.info("Consuming Following Event : " + payload);
        try {
            FollowingEvent followingEvent = objectMapper.readValue(payload.value(), FollowingEvent.class);
            redisTemplate.opsForList().leftPush(String.join(":", Constant.RedisKey.FOLLOWING, followingEvent.getFolloweeId()), followingEvent.getFollowerId());
            trimFollowerListAsync(followingEvent);
        } catch (Exception e) {
            logger.error("Could not process event : {}", e.getMessage());
        }
    }

    private void trimFollowerListAsync(FollowingEvent followingEvent) {
        CompletableFuture.runAsync(() ->
        {
            try {
                Long totalFollowers = redisTemplate.opsForList().size(String.join(":", Constant.RedisKey.FOLLOWING, followingEvent.getFolloweeId()));
                if (null != totalFollowers && totalFollowers > 10000) {
                    redisTemplate.opsForList().trim(String.join(":", Constant.RedisKey.FOLLOWING, followingEvent.getFolloweeId()), 0, 1000);
                }
            }
            catch (Exception e) {
                logger.error("Could not trim the follower list : {}", e.getMessage());
            }
        });
    }
}
