package com.emyasa.spring.integration.java.ch2.orderservice;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Publisher;

@MessageEndpoint
public class WarehouseService {

    @Publisher(channel = "inputChannel")
    public String updateInventory(String message) {
        return message;
    }

}
