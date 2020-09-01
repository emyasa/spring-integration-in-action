package com.emyasa.spring.integration.java.ch1p2;

import org.springframework.integration.annotation.MessageEndpoint;

@MessageEndpoint
public interface HelloService {

    String sayHello(String name);

}
