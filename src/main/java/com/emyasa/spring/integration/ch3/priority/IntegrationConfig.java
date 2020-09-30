package com.emyasa.spring.integration.ch3.priority;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PriorityChannel;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsInboundGateway;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

@Configuration
public class IntegrationConfig {

    @Value("${spring.activemq.username}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${broker.client.id}")
    private String brokerClientId;

    @Value("${topic.name}")
    private String topic;

    @Autowired
    private Comparator reservationMessageComparator;

    @Bean
    public JmsInboundGateway jmsInboundGateway() {
        JmsInboundGateway inboundGateway = new JmsInboundGateway(messageListenerContainer(), channelPublishingJmsMessageListener());
        inboundGateway.setRequestChannel(priorityChannel());
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
        messageListenerContainer.setMessageConverter(jacksonJmsMessageConverter());
        return messageListenerContainer;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public SingleConnectionFactory singleConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory(activeMQConnectionFactory);
        singleConnectionFactory.setClientId(brokerClientId);
        singleConnectionFactory.setReconnectOnException(true);
        return singleConnectionFactory;
    }

    @Bean
    public ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener() {
        ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener = new ChannelPublishingJmsMessageListener();
        channelPublishingJmsMessageListener.setExpectReply(true);
        return channelPublishingJmsMessageListener;
    }

    @Bean
    public MessageChannel priorityChannel() {
        PriorityChannel pq = new PriorityChannel(10, reservationMessageComparator);
        return pq;
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(5000, TimeUnit.MILLISECONDS));
        return pollerMetadata;
    }
}
