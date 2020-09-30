package com.emyasa.spring.integration.ch3.topic.publish;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Publisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@MessageEndpoint
public class TopicPublisherService {

    @Publisher("channel")
    public String sendTopic(String message) {
        return message;
    }

    @Publisher("channel")
    public Message<Reservation> submitReservation(String customer, boolean priority) {
        Reservation reservation = new Reservation(customer, priority);
        return MessageBuilder.withPayload(reservation)
                .build();
    }

}
