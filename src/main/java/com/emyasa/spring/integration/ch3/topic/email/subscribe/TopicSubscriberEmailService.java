package com.emyasa.spring.integration.ch3.topic.email.subscribe;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
public class TopicSubscriberEmailService {

    @ServiceActivator(inputChannel = "channel")
    public void subscribe(String message) throws InterruptedException {
        System.out.println("Received: " + message);
        Thread.sleep(10000);
    }

}
