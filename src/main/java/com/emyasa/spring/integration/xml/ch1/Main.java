package com.emyasa.spring.integration.xml.ch1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ch1/context.xml");
        MessageChannel channel = context.getBean("names", MessageChannel.class);

        Message<String> message = MessageBuilder.withPayload("World").build();
        channel.send(message);
    }

}
