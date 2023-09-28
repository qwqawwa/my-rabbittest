package com.my.rabbitmq.service;

import com.my.rabbitmq.utils.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import org.springframework.amqp.rabbit.connection.Connection;

/**
 * @ClassName Producer
 * @Description 生产者，发消息给消息中间件
 * @Author Cheng Liu
 * @Date 2022/8/19 10:27
 */
public class ProducerTestMultiConnect {

    //队列名称
    public static final String QUEUE_NAME = "test";

    public static final int CONNECT_COUNT = 1000;
    public static final int COUNT = 20;
    //发消息
    public static void producerExec() throws Exception {

        //ConnectionFactory factory = RabbitMQUtil.getFactory();



        for (int j = 0; j < CONNECT_COUNT; j++) {
            ConnectionFactory factory = RabbitMQUtil.getFactory();
            //建立连接
            Connection connection = factory.newConnection();
            //Connection connection = factory.createConnection();
            //获取信道（通道）
            Channel channel = connection.createChannel(1);
            //创建线程池
            ExecutorService service = Executors.newCachedThreadPool();
            for (int i = 0; i < COUNT; i++) {
                int finalI = i;
                service.submit(() -> {
                    System.out.println("i : " + finalI + "|线程名称：" + Thread.currentThread().getName());
                    try {

                        /*创建队列
                         * 1.队列名称
                         * 2.是否持久化
                         * 3.是否消息共享，一条消息可以多个消费者消费
                         * 4.是否自动删除
                         * 5.其他参数
                         * */
                        //channel.queueDeclare(QUEUE_NAME,false,false,false,null);
                        String message = "Hello RabbitMQ";
                        //channel.queueDeclare().getQueue();
                        /**
                         * 发送消息
                         * 1.发到哪个交换机
                         * 2.路由key 如果没有可以写队列名称
                         * 3.其他参数信息
                         * 4.发送的消息
                         **/
                        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
                        System.out.println("消息发送成功");

                        /*channel.close();
                        connection.close();*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            Thread.sleep(100);
        }


    }
}