package com.emyasa.spring.integration.java.ch2.orderservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.class})
@TestPropertySource(locations="classpath:application-ch2.properties")
public class IntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void placeOrder() {
        orderService.placeOrder("Order is placed on queue");
    }

}
