package com.emyasa.spring.integration.java.ch1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.class})
public class IntegrationTest {

    @Autowired
    @Qualifier("names")
    private MessageChannel channel;

    @Test
    public void activateServiceCall() {
        Message<String> message = MessageBuilder.withPayload("World").build();
        channel.send(message);
    }

}
