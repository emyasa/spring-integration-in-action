package com.emyasa.spring.integration.ch3.topic.subscribe;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsInboundGateway;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.concurrent.TimeUnit;


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
    @BridgeTo(value = "notificationChannel")
    public MessageChannel pubSubChannel() {
        return new PublishSubscribeChannel(); // Synchronous - SubscribableChannel
    }

    @Bean
    public MessageChannel notificationChannel() {
        return new QueueChannel();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(10, TimeUnit.MILLISECONDS));
        return pollerMetadata;
    }
}
