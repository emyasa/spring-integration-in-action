package com.emyasa.spring.integration.ch3.datatype;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class)
@TestPropertySource(locations = "classpath:application-ch2.properties")
public class IntegrationTest {

    @Autowired
    public OrderService orderService;

    @Test
    public void placeOrder_order() {
        orderService.placeOrder(new Order("0001"));
    }

    @Test(expected = MessageDeliveryException.class)
    public void placeOrder_string() {
        orderService.placeOrder("0002");
    }

}
