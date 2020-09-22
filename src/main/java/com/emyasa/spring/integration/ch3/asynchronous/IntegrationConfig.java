package com.emyasa.spring.integration.ch3.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.jms.DynamicJmsTemplate;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import javax.jms.ConnectionFactory;

@Configuration
public class IntegrationConfig {

    @Value("${order.queue.name}")
    private String orderQueue;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    @ServiceActivator(inputChannel = "channel")
    public MessageHandler jmsOutboundAdapter() {
        JmsTemplate jmsTemplate = new DynamicJmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        JmsSendingMessageHandler messageHandler = new JmsSendingMessageHandler(jmsTemplate);
        messageHandler.setDestinationName(orderQueue);
        return messageHandler;
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(10, TimeUnit.MILLISECONDS));
        return pollerMetadata;
    }

    @Bean
    public MessageChannel channel() {
        return new QueueChannel();
    }



}
