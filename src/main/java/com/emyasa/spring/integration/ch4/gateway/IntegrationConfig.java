package com.emyasa.spring.integration.ch4.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jms.JmsOutboundGateway;
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
