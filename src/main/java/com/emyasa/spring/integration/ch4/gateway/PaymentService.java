package com.emyasa.spring.integration.ch4.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Autowired
    private PaymentGateway paymentGateway;

    @Transactional
    public void processPayment() {
        Message<String> paymentMessage = MessageBuilder.withPayload("Payment")
                .build();

        Message<String> confirmation = paymentGateway.sendPayment(paymentMessage);
        System.out.println(confirmation.getPayload());
    }

}
