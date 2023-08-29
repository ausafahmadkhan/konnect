package com.konnect.kafkaconsumer.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ITopicListener {
    void consume(ConsumerRecord<String, String> payload);
}
