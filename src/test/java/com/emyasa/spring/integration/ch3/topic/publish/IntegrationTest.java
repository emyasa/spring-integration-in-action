package com.emyasa.spring.integration.ch3.topic.publish;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class)
@TestPropertySource(locations = "classpath:application-ch2.properties")
public class IntegrationTest {

    @Autowired
    private TopicPublisherService pubSubService;

    @Test
    public void sendToMany() {
        pubSubService.sendTopic("send to all subs");
    }

}
