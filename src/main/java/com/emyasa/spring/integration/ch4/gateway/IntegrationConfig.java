package com.emyasa.spring.integration.ch4.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.DynamicJmsTemplate;
import org.springframework.integration.jms.JmsInboundGateway;
import org.springframework.integration.jms.JmsOutboundGateway;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.integration.jms.config.JmsInboundChannelAdapterParser;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.concurrent.TimeUnit;

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
    @ServiceActivator(inputChannel = "channel")
    public MessageHandler jmsOutboundGateway() {
        JmsOutboundGateway outboundGateway = new JmsOutboundGateway();
        outboundGateway.setConnectionFactory(connectionFactory);
        outboundGateway.setRequestDestinationName(orderQueue);
        outboundGateway.setRequiresReply(true);
        outboundGateway.setReplyDestinationName(orderReplyQueue);

        return outboundGateway;
    }

    @Bean
    public MessageChannel channel() {
        return new DirectChannel();
    }

}
