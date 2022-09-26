package com.my.rabbitmq.service;

import com.my.rabbitmq.utils.RabbitMQUtil;
import com.rabbitmq.client.*;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author Cheng Liu
 * @Date 2022/8/19 10:53
 */
public class Consumer {

    //队列名称
    public static final String QUEUE_NAME = "mqtt40w-test2";

    //接收消息
    public static void consumerExec() throws Exception {

        ConnectionFactory factory = RabbitMQUtil.getFactory();
        //建立连接
        Connection connection = factory.newConnection();
        //获取信道（通道）
        Channel channel = connection.createChannel();
        //声明接收消息回调
        DeliverCallback deliverCallback = ( consumerTag,  message) -> {
            System.out.println("消息接收，内容为："+new String(message.getBody()));
        };

        //取消消息时的回调
        CancelCallback cancelCallback = ( consumerTag) ->{
            System.out.println("消息消费被取消");
        };

        channel.basicQos(0,10,false);
        /**
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费成功后自否自动应答
         * 3.未成功消费的回调
         * 4.消费者取消消费的回调
         **/
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        Thread.sleep(10000);
        //channel.close();
        //connection.close();

    }
}