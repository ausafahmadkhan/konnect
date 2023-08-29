package com.ranking.rankingplatform.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konnect.kafkaconsumer.listener.ITopicListener;
import com.post.postcontract.event.PostActionEvent;
import com.ranking.rankingplatform.service.IAffinityService;
import com.ranking.rankingplatform.utility.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Component("PostAction")
public class PostActionListener implements ITopicListener {

    Logger logger = LogManager.getLogger(PostActionListener.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private IAffinityService affinityService;

    public void consume(ConsumerRecord<String, String> payload) {
        if (!payload.topic().equals(Constant.KafkaTopic.POST_ACTION)) {
            return;
        }
        logger.info("Consuming Post Action Event : " + payload);
        try {
            PostActionEvent postActionEvent = objectMapper.readValue(payload.value(), PostActionEvent.class);
            affinityService.addAction(postActionEvent);
        } catch (JsonProcessingException e) {
            logger.error("Could not send convert post action event : {}", e.getMessage());
        }
    }
}
