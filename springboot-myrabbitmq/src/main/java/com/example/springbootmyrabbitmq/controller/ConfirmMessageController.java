package com.example.springbootmyrabbitmq.controller;

import com.example.springbootmyrabbitmq.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @ClassName ConfirmMessageController
 * @Description 发布确认
 *  1.单个确认
 *  2.批量确认
 *  3.异步批量确认
 * @Author Cheng Liu
 * @Date 13/09/2022 11:51 AM
 */
public class ConfirmMessageController {

    public static final int SEND_COUNT = 10;

    public static void main(String[] args) throws Exception {
        publishMessageAsync();
    }

    //单个确认
    public static void publishMessageIndividual() throws Exception {
        ConnectionFactory factory = RabbitMQUtil.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,true,false,null);
        //开启发布确认
        channel.confirmSelect();

        long begin = System.currentTimeMillis();
        for (int i = 0; i < SEND_COUNT; i++) {
            String message = String.valueOf(i);
            //Params:
            //exchange – the exchange to publish the message to
            //routingKey – the routing key
            //props – other properties for the message - routing headers etc
            //body – the message body
            channel.basicPublish("",queueName,null,message.getBytes());
            boolean isConfirm = channel.waitForConfirms();
            if(isConfirm){
                System.out.println("消息发送成功");
            }
        }
    }

    public static void publishMessageAsync() throws Exception {
        ConnectionFactory factory = RabbitMQUtil.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
      /*  Params:
        queue – the name of the queue
        durable – true if we are declaring a durable queue (the queue will survive a server restart)
        exclusive – true if we are declaring an exclusive queue (restricted to this connection)
        autoDelete – true if we are declaring an autodelete queue (server will delete it when no longer in use)
        arguments – other properties (construction arguments) for the queue*/
        channel.queueDeclare(queueName,true,false,false,null);

        //开启发布确认模式
        channel.confirmSelect();

        /*所以需要使用ConcurrentLinkedQueue/ConcurrentSkipListMap/ConcurrentNavigableMap
         *来进行confirm callbacks 与发布线程之间进行消息的传递
         *需要线程安全、有序的哈希表，适应于高并发
         *1.将序号和消息关联
         *2.批量删除map里的内容，只需要给到序号
         * 3.支持高并发（多线程）
         */
        //ConcurrentLinkedQueue<String> outstandingConfirm = new ConcurrentLinkedQueue<>();
        ConcurrentSkipListMap<Long,String> outstandingConfirm = new ConcurrentSkipListMap<>();


        //回调函数ackCallback 消息确认成功
        /*
        Params：
        deliveryTag：消息的编号
        multiple：是否批量确认*/
        ConfirmCallback ackCallback = (deliveryTag,multiple)-> {
            System.out.println("成功应答"+deliveryTag);
        };

        //回调函数nackCallback 消息确认失败
        ConfirmCallback nackCallback = (deliveryTag,multiple)-> {
            //拿到发布确认失败的消息后之后再做处理
            String message = outstandingConfirm.get(deliveryTag);
            System.out.println("失败应答，需要记录并重发"+message);

        };

        /*监听器
        Params:
        ackCallback – callback on ack
        nackCallback – call on nack (negative ack)*/
        channel.addConfirmListener(ackCallback,nackCallback);

        for (int i = 0; i < SEND_COUNT; i++) {
            Thread.sleep(1000);
            String message = String.valueOf(i);

            /*这里需要做一个消息的记录，目的是为了防止消息丢失后，消息失败回调函数nackCallback无法找到相应的消息内容
            因为监听器回调与消息发布线程是异步的
            所以需要使用ConcurrentLinkedQueue/ConcurrentSkipListMap/ConcurrentNavigableMap
            来进行confirm callbacks 与发布线程之间进行消息的传递*/
            outstandingConfirm.put(channel.getNextPublishSeqNo(),message);

            /*Params:
            exchange – the exchange to publish the message to
            routingKey – the routing key
            props – other properties for the message - routing headers etc
            body – the message body*/
            channel.basicPublish("",queueName,null,message.getBytes());



        }
        long end = System.currentTimeMillis();
        System.out.println("end"+end);


    }

}