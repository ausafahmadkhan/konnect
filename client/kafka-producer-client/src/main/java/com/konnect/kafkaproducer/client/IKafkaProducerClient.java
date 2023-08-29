package com.konnect.kafkaproducer.client;

public interface IKafkaProducerClient {
    void sendMessage(String message, String topic);
}
