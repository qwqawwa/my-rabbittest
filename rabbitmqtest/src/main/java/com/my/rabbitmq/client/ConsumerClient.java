package com.my.rabbitmq.client;

import com.my.rabbitmq.service.Consumer;
import com.my.rabbitmq.utils.RabbitMQConstant;
import com.my.rabbitmq.utils.RabbitMQUtil;
import com.rabbitmq.client.Channel;

/**
 * @ClassName ConsumerClient
 * @Description 消费者客户端
 * @Author Cheng Liu
 * @Date 09/12/2022 11:08 PM
 */
public class ConsumerClient {

    public static void main(String[] args) throws Exception {
        //1.connection - 基于长连接方式(一个站点保持使用一个connection对象)
        //2.channel- 基于长链接方式(生产者和消费者采用相同的处理方式)
        Channel channel = RabbitMQUtil.getChannel();
        //队列的初始化声明，只需要每次初始化声明即可
        //模拟客户端声明多个queue
        RabbitMQUtil.initQueueDeclare(channel, RabbitMQConstant.DEMO_QUEUE_NAME);
        RabbitMQUtil.initQueueDeclare(channel, RabbitMQConstant.MY_TEST_QUEUE_NAME);
        //消费者客户端一般会一直启动作为监听，程序关闭时connection关闭，channel随connection关闭
        Consumer.consumerExec(channel,RabbitMQConstant.DEMO_QUEUE_NAME);
        Consumer.consumerExec(channel,RabbitMQConstant.MY_TEST_QUEUE_NAME);
    }
}