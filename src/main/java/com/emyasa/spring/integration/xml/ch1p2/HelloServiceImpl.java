package com.emyasa.spring.integration.xml.ch1p2;

class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
