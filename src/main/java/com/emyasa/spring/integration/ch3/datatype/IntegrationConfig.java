package com.emyasa.spring.integration.ch3.datatype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.interceptor.MessageSelectingInterceptor;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.integration.selector.PayloadTypeSelector;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.jms.ConnectionFactory;

@Configuration
public class IntegrationConfig {

    @Value("${order.queue.name}")
    public String orderQueue;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    @ServiceActivator(inputChannel = "channel")
    public MessageHandler jmsOutboundGateway() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());

        JmsSendingMessageHandler messageHandler = new JmsSendingMessageHandler(jmsTemplate);
        messageHandler.setDestinationName(orderQueue);
        return messageHandler;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public MessageChannel channel() {
        DirectChannel channel = new DirectChannel();
        channel.addInterceptor(selectingInterceptor());
        return channel;
    }

    @Bean
    public MessageSelectingInterceptor selectingInterceptor() {
        return new MessageSelectingInterceptor(payloadTypeSelector());
    }

    @Bean
    public PayloadTypeSelector payloadTypeSelector() {
        return new PayloadTypeSelector(Order.class);
    }
}
