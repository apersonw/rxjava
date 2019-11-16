package org.rxjava.common.bus;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author happy 2019-05-02 14:32
 * 消息总线配置
 */
@Configuration
public class RxBusConfiguration {
    /**
     * 交换机名称
     */
    static final String EXCHANGE = "rxjava-fanout-exchange";
    /**
     * 队列名前缀
     */
    private static final String QUEUE_NAME_PREFIX = "rxjava-queue.";
    /**
     * 应用名
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * fanout交换机
     */
    @Bean
    @Qualifier("fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE);
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
