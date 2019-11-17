package org.rxjava.common.bus;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author happy 2019-05-02 14:32
 * 消息总线配置
 */
@Configuration
public class RxBusConfiguration {
    /**
     * fanout交换机名称
     */
    static final String FANOUT_EXCHANGE = "rxjava-fanout-exchange";
    /**
     * 队列名前缀
     */
    private static final String QUEUE_NAME_PREFIX = "rxjava-queue.";
    /**
     * delay队列名前缀
     */
    static final String QUEUE_NAME_DELAY_PREFIX = "rxjava-delay-queue.";
    /**
     * 应用名
     */
    @Value("${spring.application.name}")
    String applicationName;

    /**
     * fanout交换机
     */
    @Bean
    @Qualifier("fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /**
     * 队列
     */
    @Bean
    @ConditionalOnBean(BusReceiver.class)
    @Qualifier("queue")
    public Queue queue() {
        return new Queue(QUEUE_NAME_PREFIX + applicationName, true);
    }

    /**
     * delay队列
     */
    @Bean
    @ConditionalOnBean(BusReceiver.class)
    @Qualifier("delayQueue")
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", FANOUT_EXCHANGE);
        args.put("x-dead-letter-router-key", QUEUE_NAME_PREFIX + "#");
        return new Queue(QUEUE_NAME_DELAY_PREFIX + applicationName, true, false, false, args);
    }

    /**
     * 绑定交换机和队列
     */
    @Bean
    @ConditionalOnBean(BusReceiver.class)
    @Qualifier("fanoutBinding")
    public Binding fanoutBinding(
            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange,
            @Qualifier("queue") Queue queue
    ) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /**
     * 消息发射bean
     */
    @Bean
    public BusEmitter busEmitter() {
        return new BusEmitter();
    }

    /**
     * 消息接收bean
     */
    @Bean
    @ConditionalOnBean(BusReceiver.class)
    @Qualifier("messageReceiver")
    public MessageReceiver messageReceiver() {
        return new MessageReceiver();
    }

    /**
     * 消息监听适配器
     */
    @Bean
    @ConditionalOnBean(BusReceiver.class)
    @Qualifier("messageListenerAdapter")
    public MessageListenerAdapter messageListenerAdapter(@Qualifier("messageReceiver") MessageReceiver messageReceiver) {
        return new MessageListenerAdapter(messageReceiver, "receiveMessage");
    }

    /**
     * 消息监听容器
     */
    @Bean
    @ConditionalOnBean(BusReceiver.class)
    public SimpleMessageListenerContainer busContainer(
            ConnectionFactory connectionFactory,
            @Qualifier("messageListenerAdapter") MessageListenerAdapter messageListenerAdapter
    ) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME_PREFIX + applicationName);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }
}
