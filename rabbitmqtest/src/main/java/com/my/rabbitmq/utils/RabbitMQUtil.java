package com.my.rabbitmq.utils;

//import com.rabbitmq.client.Channel;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
//import org.springframework.amqp.rabbit.connection.Connection;

/**
 * @ClassName RabbitMQUtil
 * @Description TODO
 * @Author Cheng Liu
 * @Date 2022/8/19 14:13
 */
public class RabbitMQUtil {

    //队列名称
    public static final String MQTT_QUEUE_NAME = "MyTestQueue";

    public static final String EXCHANGE_NAME = "amq.topic";

    public static ConnectionFactory getFactory() throws Exception {

        //ConnectionFactory factory = new ConnectionFactory();
        ConnectionFactory factory = new ConnectionFactory();
        //MQ所在服务器IP
        factory.setHost("172.18.1.8");
        //factory.setHost("192.168.1.101");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        return factory;
    }



    public static ConnectionFactory getMQTTFactory() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        //MQ所在服务器IP
        factory.setHost("172.18.1.8");
        factory.setPort(5670);
        factory.setUsername("guest");
        factory.setPassword("guest");

        return factory;
    }

    /**
     * 获取信道
     * @return channel
     * @throws Exception
     */
    public static Channel getChannel() throws Exception {

        Channel channel = null;
        try{
            ConnectionFactory factory = getFactory();
            //建立连接
            Connection connection = factory.newConnection();
            //获取信道（通道）
            channel = connection.createChannel();

        }catch (Exception e){
            //通道信道异常处理
        }
        return channel;
    }

    /**
     * 获取连接
     * @return Connection
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {

        Connection connection = null;
        try{
            ConnectionFactory factory = getFactory();
            //建立连接
            connection = factory.newConnection();

        }catch (Exception e){
            //通道信道异常处理
        }
        return connection;
    }


    /**
     * 通过连接获取信道
     * @param connection
     * @return
     * @throws Exception
     */
    public static Channel getChannelByConn(Connection connection) throws Exception {

        Channel channel = null;
        try{
            //获取信道（通道）
            channel = connection.createChannel();

        }catch (Exception e){
            //通道信道异常处理
        }
        return channel;
    }
    /**
     * 初始化声明queue
     * @param channel
     * @param queueName
     * @throws IOException
     */
    public static void initQueueDeclare(Channel channel,String queueName) throws IOException {

        /* 创建队列
         * 1.队列名称
         * 2.是否持久化
         * 3.是否消息共享，一条消息可以多个消费者消费
         * 4.是否自动删除
         * 5.其他参数
          * */
        channel.queueDeclare(queueName,true,false,false,null);
    }

  /*  public static void getConnection() throws Exception {

        ConnectionFactory factory = getFactory();

        //建立连接
        Connection connection = factory.newConnection();
        //获取信道（通道）
        Channel channel = connection.createChannel(1);


        *//*创建队列
         * 1.队列名称
         * 2.是否持久化
         * 3.是否消息共享，一条消息可以多个消费者消费
         * 4.是否自动删除
         * 5.其他参数
         * *//*
        //channel.queueDeclare(MQTT_QUEUE_NAME,true,false,false,null);

        *//*绑定队列与交换机
         * 参数：
         * 1.队列名称
         * 2.交换机名称
         * 3.路由键，绑定规则（如果减缓及类型为Fanout,那么对应的routing key为空字符串）*//*
        channel.queueBind(MQTT_QUEUE_NAME,EXCHANGE_NAME,"*2test*");
    }*/

   /* public static void getCachingConnection() throws Exception {

     *//*   ConnectionFactory factory = getFactory();
        Connection connection = factory.newConnection();*//*
        //建立连接
        CachingConnectionFactory factory = getFactory();
        Connection connection = factory.createConnection();
        //获取信道（通道）
        Channel channel = connection.createChannel(true);


        *//*创建队列
         * 1.队列名称
         * 2.是否持久化
         * 3.是否消息共享，一条消息可以多个消费者消费
         * 4.是否自动删除
         * 5.其他参数
         * *//*
        //channel.queueDeclare(MQTT_QUEUE_NAME,true,false,false,null);

        *//*绑定队列与交换机
         * 参数：
         * 1.队列名称
         * 2.交换机名称
         * 3.路由键，绑定规则（如果减缓及类型为Fanout,那么对应的routing key为空字符串）*//*
        channel.queueBind(MQTT_QUEUE_NAME,EXCHANGE_NAME,"*2test*");
    }*/
}