package com.emyasa.spring.integration.java.ch1p2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.class})
public class IntegrationTest {

    @Autowired
    @Qualifier("helloGateway")
    private GatewayService gatewayService;

    @Test
    public void sayHelloWorld() {
        System.out.println(gatewayService.sayHello("World"));
    }
}
