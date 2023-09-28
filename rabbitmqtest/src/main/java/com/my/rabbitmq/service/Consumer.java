package com.my.rabbitmq.service;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Consumer
 * @Description 消费者
 * @Author Cheng Liu
 * @Date 2022/8/19 10:53
 */
@Slf4j
public class Consumer {

    //接收消息
    public static void consumerExec(Channel channel,String queueName) throws Exception {

        //声明接收消息回调
        DeliverCallback deliverCallback = ( consumerTag,  message) -> {

            //手动ack
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            log.info("消息接收，内容为： " + message.getBody());
        };

        //取消消息时的回调
        CancelCallback cancelCallback = ( consumerTag) ->{
            log.warn("消息消费被取消");
        };

        /**
         * 消息服务质量设置
         * prefetchSize -服务器将传递的最大内容量(以字节为单位)，如果没有限制则为0
         * prefetchCount -服务器将传递的最大消息数，如果没有限制则为0
         * global - 如果设置应该应用于整个通道而不是单个消费者，则为true
         * */
        channel.basicQos(0,0,false);
        /**
         * 消费者消费消息
         * 1.消费哪个队列:队列名称
         * 2.消费成功后自否自动应答,比较可靠的方式是：设置为false，并且在callback里做手动ack
         * 3.未成功消费的回调
         * 4.消费者取消消费的回调
         **/
        channel.basicConsume(queueName,false,deliverCallback,cancelCallback);

        //channel.close();
        //connection.close();

    }
}