package com.konnect.kafkaconsumer.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ListenerFactory {

    @Autowired
    private Map<String, ITopicListener> listenerMap;

    public ITopicListener getTopicListener(String topicName) {
        return listenerMap.get(topicName);
    }
}
