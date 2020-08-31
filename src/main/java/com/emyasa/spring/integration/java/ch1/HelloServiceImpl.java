package com.emyasa.spring.integration.java.ch1;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
class HelloServiceImpl implements HelloService {

    @Override
    @ServiceActivator(inputChannel = "names")
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }

}
