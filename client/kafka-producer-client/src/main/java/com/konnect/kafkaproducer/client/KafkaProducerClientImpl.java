package com.konnect.kafkaproducer.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerClientImpl implements IKafkaProducerClient {
    Logger logger = LogManager.getLogger(KafkaProducerClientImpl.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String message, String topic) {
        logger.info("Sending message to {}", topic);
        kafkaTemplate.send(topic, message);
    }
}