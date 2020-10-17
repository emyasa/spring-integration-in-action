package com.emyasa.spring.integration.ch5.transformer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PriorityChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsInboundGateway;
import org.springframework.integration.json.JsonToObjectTransformer;
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
        inboundGateway.setRequestChannel(inputChannel());
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
    @Transformer(inputChannel = "inputChannel", outputChannel = "priorityChannel")
    public JsonToObjectTransformer jsonToObjectTransformer() {
        return new JsonToObjectTransformer(Reservation.class);
    }

    @Bean
    public MessageChannel inputChannel() {
        PriorityChannel queueChannel = new PriorityChannel(100, reservationMessageComparator);
        return queueChannel;
    }

    @Bean
    public MessageChannel priorityChannel() {
        PriorityChannel pq= new PriorityChannel(100);
        return pq;
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(10000, TimeUnit.MILLISECONDS));
        return pollerMetadata;
    }

}
