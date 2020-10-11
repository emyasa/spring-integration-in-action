package com.emyasa.spring.integration.ch4.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class)
@TestPropertySource(locations = "classpath:application-ch2.properties")
public class IntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Test
    public void processPayment() {
        paymentService.processPayment();
    }

}
