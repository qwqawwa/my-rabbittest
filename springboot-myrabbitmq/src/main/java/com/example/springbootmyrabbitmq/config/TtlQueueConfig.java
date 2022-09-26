package com.example.springbootmyrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName TtlQueueConfig
 * @Description 队列配置类
 * @Author Cheng Liu
 * @Date 06/09/2022 10:18 PM
 */
@Configuration
public class TtlQueueConfig {

    //普通交换机名称
    public static final String EXCHANGE_X = "X";
    //死信交换机名称
    public static final String EXCHANGE_DEAD_LETTER = "D";
    //普通队列名称A
    public static final String QUEUE_A = "Queue_A";
    //普通队列名称B
    public static final String QUEUE_B = "Queue_B";
    //死信队列名称D
    public static final String QUEUE_D = "Queue_D";
    //普通交换机路由键
    public static final String ROUTING_KEY_X = "Key_X";
    //死信交换机路由键
    public static final String ROUTING_KEY_D = "Key_D";

    //声明exchangex
    @Bean
    public DirectExchange getExchangeX(){
        return new DirectExchange(EXCHANGE_X);
    }

    //声明死信exchange
    @Bean
    public DirectExchange getExchangeDL(){
        return new DirectExchange(EXCHANGE_DEAD_LETTER);
    }

    //声明队列A
    @Bean
    public Queue getQueueA(){
        QueueBuilder queueBuilder = QueueBuilder.durable(QUEUE_A);
        //ttl参数设置为毫秒
        queueBuilder.ttl(10000);
        queueBuilder.deadLetterExchange(EXCHANGE_DEAD_LETTER);
        queueBuilder.deadLetterRoutingKey(ROUTING_KEY_D);
        Queue queue = queueBuilder.build();
        return queue;
    }

    //声明队列B
    @Bean
    public Queue getQueueB(){
        QueueBuilder queueBuilder = QueueBuilder.durable(QUEUE_B);
        queueBuilder.ttl(10000);
        queueBuilder.deadLetterExchange(EXCHANGE_DEAD_LETTER);
        queueBuilder.deadLetterRoutingKey(ROUTING_KEY_D);
        Queue queue = queueBuilder.build();
        return queue;
    }

    //声明死信队列D
    @Bean
    public Queue getQueueD(){
        QueueBuilder queueBuilder = QueueBuilder.durable(QUEUE_D);
        Queue queue = queueBuilder.build();
        return queue;
    }

    //声明绑定
    @Bean
    public Binding queueABingdingX(@Qualifier("getExchangeX") DirectExchange xExchange,
                                   @Qualifier("getQueueA") Queue queueA){
        return BindingBuilder.bind(queueA).to(xExchange).with(ROUTING_KEY_X);
    }

    //声明绑定
    @Bean
    public Binding queueBBingdingX(@Qualifier("getExchangeX") DirectExchange xExchange,
                                   @Qualifier("getQueueB") Queue queueB){
        return BindingBuilder.bind(queueB).to(xExchange).with(ROUTING_KEY_X);
    }

    //声明绑定
    @Bean
    public Binding queueDBingdingDL(@Qualifier("getExchangeDL") DirectExchange dlExchange,
                                   @Qualifier("getQueueD") Queue queueD){
        return BindingBuilder.bind(queueD).to(dlExchange).with(ROUTING_KEY_D);
    }


   /* @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualhost);
        //connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }*/


}