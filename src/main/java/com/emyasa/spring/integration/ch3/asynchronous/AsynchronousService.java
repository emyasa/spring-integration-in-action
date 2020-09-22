package com.emyasa.spring.integration.ch3.asynchronous;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Publisher;

@MessageEndpoint
public class AsynchronousService {

    @Publisher("channel")
    public String sendMessageAsynchronously(String message) {
        return message;
    }

}
