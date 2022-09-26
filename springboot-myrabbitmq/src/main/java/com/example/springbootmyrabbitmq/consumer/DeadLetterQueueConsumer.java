package com.example.springbootmyrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName DeadLetterQueueConsumer
 * @Description 死信队列消费者
 * @Author Cheng Liu
 * @Date 07/09/2022 9:47 PM
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    public static final String QUEUE_NAME = "Queue_D";

    @RabbitListener(queues = QUEUE_NAME)
    public void recieveDeadLetterMsg(Message msg, Channel channel){

        DeliverCallback deliverCallback =  (consumerTag, message) -> {
            String msgString = new String(msg.getBody());
            log.info("收到消息，死信队列中消息为： " + msgString);
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);

        };
        System.out.println("接收死信消息");


    }
}