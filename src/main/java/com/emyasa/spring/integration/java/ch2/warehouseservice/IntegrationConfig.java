package com.emyasa.spring.integration.java.ch2.warehouseservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsInboundGateway;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.messaging.MessageChannel;

import javax.jms.ConnectionFactory;

@Configuration
public class IntegrationConfig {

    @Value("${order.queue.name}")
    private String orderQueueName;

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
        messageListenerContainer.setDestinationName(orderQueueName);
        messageListenerContainer.setSessionTransacted(true);
        return messageListenerContainer;
    }

    @Bean
    public ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener() {
        ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener = new ChannelPublishingJmsMessageListener();
        channelPublishingJmsMessageListener.setExpectReply(true);
        return channelPublishingJmsMessageListener;
    }

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

}
