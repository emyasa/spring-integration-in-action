package com.emyasa.spring.integration.java.ch1p2;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "helloGateway", defaultRequestChannel = "names")
public interface HelloService {

    String sayHello(String name);

}
