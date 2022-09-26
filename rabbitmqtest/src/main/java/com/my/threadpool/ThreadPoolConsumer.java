package com.my.threadpool;

import com.my.rabbitmq.service.Consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ThreadPoolConsumer
 * @Description TODO
 * @Author Cheng Liu
 * @Date 2022/8/19 14:38
 */
public class ThreadPoolConsumer {

    public static final int COUNT = 300000;

    public static void main(String[] args) {
        //创建线程池
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < COUNT; i++) {
            int finalI = i;
            service.submit(() -> {
                System.out.println("i : " + finalI + "|线程名称：" + Thread.currentThread().getName());
                try {
                    Consumer.consumerExec();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}

