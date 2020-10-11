package com.emyasa.spring.integration.ch4.gateway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway(name = "paymentGateway", defaultRequestChannel = "channel")
public interface PaymentGateway {

    Message<String> sendPayment(Message<String> payment);

}
