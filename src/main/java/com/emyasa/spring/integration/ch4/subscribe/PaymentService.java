package com.emyasa.spring.integration.ch4.subscribe;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@MessageEndpoint
public class PaymentService {

    @ServiceActivator(inputChannel = "inputChannel")
    public Message<String> processPayment(Message<String> message) {
        System.out.println("here");

        String content = message.getPayload() + " received.";
        return MessageBuilder.withPayload(content).copyHeaders(message.getHeaders()).build();
    }

}
