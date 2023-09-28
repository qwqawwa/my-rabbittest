package com.my.threadpool;

import com.my.rabbitmq.service.Consumer;
import com.my.rabbitmq.utils.RabbitMQConstant;
import com.my.rabbitmq.utils.RabbitMQUtil;
import com.rabbitmq.client.Channel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ThreadPoolConsumer
 * @Description TODO
 * @Author Cheng Liu
 * @Date 2022/8/19 14:38
 */
public class ThreadPoolConsumer {

    public static final int COUNT = 300;

    public static void main(String[] args) throws Exception {
        //创建线程池
        ExecutorService service = Executors.newCachedThreadPool();
        Channel channel = RabbitMQUtil.getChannel();
        for (int i = 0; i < COUNT; i++) {
            int finalI = i;
            service.submit(() -> {
                System.out.println("i : " + finalI + "|线程名称：" + Thread.currentThread().getName());
                try {
                    Consumer.consumerExec(channel, RabbitMQConstant.MY_TEST_QUEUE_NAME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}

