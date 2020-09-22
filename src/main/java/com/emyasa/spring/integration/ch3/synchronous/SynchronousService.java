package com.emyasa.spring.integration.ch3.synchronous;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Publisher;

@MessageEndpoint
public class SynchronousService {

    @Publisher("inputChannel")
    public String sendMessageSynchronously(String message) {
        return message;
    }

}
