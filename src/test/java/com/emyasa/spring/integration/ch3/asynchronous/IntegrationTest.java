package com.emyasa.spring.integration.ch3.asynchronous;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Main.class })
@TestPropertySource(locations = "classpath:application-ch2.properties")
public class IntegrationTest {

    @Autowired
    private AsynchronousService asynchronousService;

    @Test
    public void sendAsynchronously() {
        long startTime = System.currentTimeMillis();
        asynchronousService.sendMessageAsynchronously("Hello word");
        System.out.println("took: " + (System.currentTimeMillis() - startTime) + " ms");
    }

}
