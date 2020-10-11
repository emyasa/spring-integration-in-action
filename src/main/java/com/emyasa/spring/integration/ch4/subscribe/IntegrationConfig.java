package com.emyasa.spring.integration.ch4.subscribe;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.DynamicJmsTemplate;
import org.springframework.integration.jms.JmsInboundGateway;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.jms.ConnectionFactory;

@Configuration
public class IntegrationConfig {

    @Value("${order.queue.name}")
    private String orderQueue;

    @Value("${order.reply.queue.name}")
    private String orderReplyQueue;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public JmsInboundGateway inboundGateway() {
        JmsInboundGateway inboundGateway = new JmsInboundGateway(messageListenerContainer(), channelPublishingJmsMessageListener());
        inboundGateway.setRequestChannel(inputChannel());
        return inboundGateway;
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setDestinationName(orderQueue);
        messageListenerContainer.setSessionTransacted(true);
        return messageListenerContainer;
    }

    @Bean
    public ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener() {
        ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener = new ChannelPublishingJmsMessageListener();
        channelPublishingJmsMessageListener.setExpectReply(true);
        channelPublishingJmsMessageListener.setDefaultReplyDestination(new ActiveMQQueue(orderReplyQueue));
        return channelPublishingJmsMessageListener;
    }

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    // ------------------------------------------

    @Bean
    @ServiceActivator(inputChannel = "replyChannel")
    public MessageHandler jmsOutboundAdapter() {
        JmsTemplate jmsTemplate = new DynamicJmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);

        JmsSendingMessageHandler messageHandler = new JmsSendingMessageHandler(jmsTemplate);
        messageHandler.setDestinationName(orderReplyQueue);
        return messageHandler;
    }

    @Bean
    public MessageChannel replyChannel() {
        return new DirectChannel();
    }

}
