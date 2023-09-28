package com.my.rabbitmq.service;

import com.rabbitmq.client.Channel;
//import org.springframework.amqp.rabbit.connection.Connection;

/**
 * @ClassName Producer
 * @Description 生产者，发消息给消息中间件
 * @Author Cheng Liu
 * @Date 2022/8/19 10:27
 */
public class Producer {

    //推送消息
    public static void producerExec(Channel channel,String queueName,String message) throws Exception {

        try {
            /*创建队列
             * 1.队列名称
             * 2.是否持久化
             * 3.是否消息共享，一条消息可以多个消费者消费
             * 4.是否自动删除
             * 5.其他参数
             * */
            //channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            /**
             * 发送消息
             * 1.发到哪个交换机
             * 2.路由key 如果没有可以写队列名称
             * 3.其他参数信息
             * 4.发送的消息
             **/
            channel.basicPublish("",queueName,null,message.getBytes());
            System.out.println("消息发送成功");

         /*   channel.close();
            connection.close();*/

        } catch (Exception e) {
            e.printStackTrace();
        }







    }
}