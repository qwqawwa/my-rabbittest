package com.my.rabbitmq.client;

import com.my.rabbitmq.service.Producer;
import com.my.rabbitmq.utils.RabbitMQConstant;
import com.my.rabbitmq.utils.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ProducerClient
 * @Description 生产者客户端
 * @Author Cheng Liu
 * @Date 09/12/2022 10:51 PM
 */
@Slf4j
public class ProducerClient  {

    private static final int COUNT = 10;

    public static void main(String[] args) throws Exception {
        //1.connection - 基于长连接方式(一个站点保持使用一个connection对象)
        //2.channel- 基于长链接方式(生产者和消费者采用相同的处理方式)
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = RabbitMQUtil.getChannelByConn(connection);

        //队列的初始化声明，只需要每次初始化声明即可
        RabbitMQUtil.initQueueDeclare(channel,RabbitMQConstant.MY_TEST_QUEUE_NAME);
        //交换机的声明

        //创建线程池，线程池模拟实际场景中的多次发送
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < COUNT; i++) {
            int finalI = i;
            service.submit(() -> {
                log.info("i : " + finalI + "|线程名称：" + Thread.currentThread().getName());
                try {

                    Producer.producerExec(channel, RabbitMQConstant.MY_TEST_QUEUE_NAME,"push test");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
            //发送结束时关闭channel和connection
            //但是注意因为channel和connection是长连接使用，等所有发送操作结束最后关闭
            channel.close();
            connection.close();

    }


}