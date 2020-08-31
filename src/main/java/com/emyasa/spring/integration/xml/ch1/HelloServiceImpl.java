package com.emyasa.spring.integration.xml.ch1;

class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }

}
