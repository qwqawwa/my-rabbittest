package com.my.threadpool;

import com.my.rabbitmq.entity.SysNotice;
import com.my.rabbitmq.utils.MqttServerUtil_bak;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ThreadPoolTest
 * @Description TODO
 * @Author Cheng Liu
 * @Date 2022/8/19 14:35
 */
public class ThreadPoolProducer {

    public static final int COUNT = 4000;

    public static void main(String[] args) throws Exception {

        //创建线程池
        ExecutorService service = Executors.newCachedThreadPool();

        SysNotice sysNotice = new SysNotice();

        //sysNotice.setMessage("Hello Test ");
        //mqttServerUtil.publish(sysNotice,"*mqtt*","");
        for (int j = 0; j < 25; j++) {
            
            for (int i = 0; i < COUNT; i++) {

                service.submit(() -> {
                    System.out.println("i : " + Thread.currentThread().getName() + "|线程名称：" + Thread.currentThread().getName());
                    try {
                        //Producer.producerExec();
                        MqttServerUtil_bak mqttServerUtil = new MqttServerUtil_bak();
                        sysNotice.setMessage("Hello Test "+Thread.currentThread().getName());

                        mqttServerUtil.publish(sysNotice,"*mqtt*");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            Thread.sleep(40000);
        }
    }



  /*  private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args) {

        //使用阿里巴巴推荐的创建线程池的方式
        //通过ThreadPoolExecutor构造函数自定义参数创建
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        MqttServerUtil mqttServerUtil = new MqttServerUtil();

       *//* for (int i = 0; i < 2; i++) {
            int finalI = i;*//*
            executor.execute(() -> {
                for (int i = 0; i < 2; i++) {
                            int finalI = i;
                try {
                    SysNotice sysNotice = new SysNotice();

                    //Thread.sleep(2000);
                    sysNotice.setMessage("Hello Test "+ finalI);
                    mqttServerUtil.publish(sysNotice,"*mqtt*",String.valueOf(finalI));
                } catch (Exception e) {
                    e.printStackTrace();
                }
               // System.out.println("CurrentThread name:" + Thread.currentThread().getName() + "date：" + Instant.now());
            });
        }
        //终止线程池
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished all threads");
    }
*/
}