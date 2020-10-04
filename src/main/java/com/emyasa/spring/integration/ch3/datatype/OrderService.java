package com.emyasa.spring.integration.ch3.datatype;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Publisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@MessageEndpoint
public class OrderService {

    @Publisher("channel")
    public Message<Order> placeOrder(Order order) {
        return MessageBuilder.withPayload(order)
                .build();
    }

    @Publisher("channel")
    public Message<String> placeOrder(String orderId) {
        return MessageBuilder.withPayload(orderId)
                .build();
    }

}
