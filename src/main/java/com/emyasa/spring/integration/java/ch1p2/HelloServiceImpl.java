package com.emyasa.spring.integration.java.ch1p2;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
public class HelloServiceImpl implements HelloService {

    @Override
    @ServiceActivator(inputChannel = "names")
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
