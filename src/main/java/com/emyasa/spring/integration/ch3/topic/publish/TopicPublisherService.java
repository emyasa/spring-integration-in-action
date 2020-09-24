package com.emyasa.spring.integration.ch3.pubsub;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Publisher;

@MessageEndpoint
public class TopicPublisherService {

    @Publisher("channel")
    public String sendTopic(String message) {
        return message;
    }

}
