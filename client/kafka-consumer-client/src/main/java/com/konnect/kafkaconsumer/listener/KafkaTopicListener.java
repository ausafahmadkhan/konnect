package com.konnect.kafkaconsumer.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicListener {
    Logger logger = LogManager.getLogger(KafkaTopicListener.class);

    @Autowired
    private ListenerFactory listenerFactory;

    @KafkaListener(topics = {"#{'${spring.kafka.consumer.topics}'.split(',')}"}, groupId = "#{'${spring.kafka.consumer.group-id}'}")
    private void consume(ConsumerRecord<String , String> payload) {
        logger.info("Received Message in group : " + payload);
        listenerFactory.getTopicListener(payload.topic()).consume(payload);
    }
}
