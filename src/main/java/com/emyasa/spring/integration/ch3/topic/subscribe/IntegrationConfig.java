package com.emyasa.spring.integration.ch3.topic.subscribe;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsInboundGateway;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.messaging.MessageChannel;


@Configuration
public class IntegrationConfig {

    @Value("${spring.activemq.username}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${topic.name}")
    private String topic;

    @Value("${broker.client.id}")
    private String brokerClientId;

    @Bean
    public JmsInboundGateway inboundGateway() {
        JmsInboundGateway inboundGateway = new JmsInboundGateway(messageListenerContainer(), channelPublishingJmsMessageListener());
        inboundGateway.setRequestChannel(pubSubChannel());
        return inboundGateway;
    }

    @Bean
    public DefaultMessageListenerContainer messageListenerContainer() {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setPubSubDomain(true);
        messageListenerContainer.setSessionTransacted(true);
        messageListenerContainer.setSubscriptionDurable(true);
        messageListenerContainer.setConnectionFactory(singleConnectionFactory());
        messageListenerContainer.setDestination(new ActiveMQTopic(topic));
        return messageListenerContainer;
    }

    @Bean
    public SingleConnectionFactory singleConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(username, password, brokerUrl);
        SingleConnectionFactory connectionFactory = new SingleConnectionFactory(factory);
        connectionFactory.setClientId(brokerClientId);
        connectionFactory.setReconnectOnException(true);
        return connectionFactory;
    }

    @Bean
    public ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener() {
        ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener = new ChannelPublishingJmsMessageListener();
        channelPublishingJmsMessageListener.setExpectReply(true);
        return channelPublishingJmsMessageListener;
    }

    @Bean
    public MessageChannel pubSubChannel() {
        return new PublishSubscribeChannel(); // Synchronous - SubscribableChannel
    }

}
