package com.emyasa.spring.integration.xml.ch1p2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ch1/context-02.xml");
        HelloService helloService = context.getBean("helloGateway", HelloService.class);
        System.out.println(helloService.sayHello("World"));
    }

}
