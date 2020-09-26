package com.emyasa.spring.integration.ch3.topic.subscribe;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
public class TopicSubscriberService {

    @ServiceActivator(inputChannel = "pubSubChannel")
    public void subscribe(String message) {
        System.out.println(message);
    }

}
