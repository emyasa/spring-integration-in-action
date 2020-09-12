package com.emyasa.spring.integration.java.ch2.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private WarehouseService warehouseService;

    @Transactional
    public void placeOrder(String message) {
        warehouseService.updateInventory(message);
    }

}
