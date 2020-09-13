package com.emyasa.spring.integration.java.ch2.warehouseservice;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.transaction.annotation.Transactional;

@MessageEndpoint
public class WarehouseService {

    @ServiceActivator(inputChannel = "inputChannel")
    @Transactional
    public void updateInventory(String message) {
        System.out.println(message);
    }

}
