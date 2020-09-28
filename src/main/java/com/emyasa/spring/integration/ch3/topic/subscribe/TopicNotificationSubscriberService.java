package com.emyasa.spring.integration.ch3.topic.subscribe;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
public class TopicNotificationSubscriberService {

    @ServiceActivator(inputChannel = "notificationChannel")
    public void subscribe(String message) throws InterruptedException {
        System.out.println("Notification process: " + message);
        Thread.sleep(10000);
    }

    @ServiceActivator(inputChannel = "pubSubChannel")
    public void anotherSubscribe(String message) throws InterruptedException {
        System.out.println("another receiver: " + message);
        Thread.sleep(10000);
    }
}
