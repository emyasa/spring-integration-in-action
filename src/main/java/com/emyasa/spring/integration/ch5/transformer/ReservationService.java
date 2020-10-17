package com.emyasa.spring.integration.ch5.transformer;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

@MessageEndpoint
public class ReservationService {

    @ServiceActivator(inputChannel = "priorityChannel")
    public void subscribe(Message<Reservation> reservationMessage) {
        System.out.println("PAYLOAD: " + reservationMessage.getPayload());
    }

}

