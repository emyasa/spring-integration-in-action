package com.emyasa.spring.integration.ch3.topic.publish;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jms.DynamicJmsTemplate;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.jms.ConnectionFactory;

@Configuration
public class IntegrationConfig {

    @Value("${topic.name}")
    private String topic;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public MessageChannel channel() {
        return new DirectChannel(); // synchronous - SubscribableChannel
    }

    @Bean
    @ServiceActivator(inputChannel = "channel")
    public MessageHandler jmsOutboundAdapter() {
        JmsTemplate jmsTemplate = new DynamicJmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());

        JmsSendingMessageHandler messageHandler = new JmsSendingMessageHandler(jmsTemplate);
        messageHandler.setDestination(new ActiveMQTopic(topic)); // create a physical topic in ActiveMQ
        return messageHandler;
    }

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        return converter;
    }

}
