package com.my.rabbitmq.service;

import com.my.rabbitmq.utils.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName ProPubSub
 * @Description TODO
 * @Author Cheng Liu
 * @Date 2022/8/21 23:14
 */
public class ProPubSub {

    //队列名称
    public static final String QUEUE_NAME1 = "MyPubSub1";

    public static final String QUEUE_NAME2 = "MyPubSub2";

    public static final String EXCHANGE_NAME = "myExchange";

    public static void pubSubExec() throws Exception {
        ConnectionFactory factory = RabbitMQUtil.getFactory();
        //建立连接
        Connection connection = factory.newConnection();
        //建立信道
        Channel channel = connection.createChannel();

        String message = "My PubSub Test";

        //创建交换机
        /*创建交换机
        * 参数：
        * 1.exchange：交换机名称
        * 2.交换机类型：
        *   Direct:定向
        *   Fanout：扇形（广播）
        *   Topic：通配符的方式
        *   Headers：参数匹配
        * 3.durable:是否持久化
        * 4.autoDelete:是否自动删除
        * 5.internal:是否内部使用（用于rabbitmq内部插件开发等，一般为false）
        * 6.arguments:参数*/
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT,false,false,false,null);

        //创建队列
        channel.queueDeclare(QUEUE_NAME1,false,false,false,null);
        channel.queueDeclare(QUEUE_NAME2,false,false,false,null);

        /*绑定队列与交换机
        * 参数：
        * 1.队列名称
        * 2.交换机名称
        * 3.路由键，绑定规则（如果减缓及类型为Fanout,那么对应的routing key为空字符串）*/
        channel.queueBind(QUEUE_NAME1,EXCHANGE_NAME,"");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"");

       /* 发消息
        1.交换机名称
        2.路由键（若Fanout则为空串）
        3.参数
        4.发消息的字节码*/
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());

        //释放资源
        channel.close();
        connection.close();
    }
}