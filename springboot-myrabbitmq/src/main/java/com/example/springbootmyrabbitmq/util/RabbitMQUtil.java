package com.example.springbootmyrabbitmq.util;

import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName RabbitMQUtil
 * @Description 工具类
 * @Author Cheng Liu
 * @Date 13/09/2022 11:56 AM
 */
public class RabbitMQUtil {

    //队列名称
    public static final String MQTT_QUEUE_NAME = "MyTestQueue";

    public static final String EXCHANGE_NAME = "amq.topic";

    public static ConnectionFactory getFactory() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        //MQ所在服务器IP
        factory.setHost("192.168.1.104");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        return factory;
    }

}