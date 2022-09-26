package com.my.threadpool;

import com.my.rabbitmq.entity.MqttConnection;
import com.my.rabbitmq.entity.SysNotice;
import com.my.rabbitmq.utils.MqttServerUtil;
import com.my.rabbitmq.utils.RabbitMQUtil;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ThreadPoolProKeepalived
 * @Description TODO
 * @Author Cheng Liu
 * @Date 04/09/2022 4:23 PM
 */
public class ThreadPoolProKeepalived {

    public static final int CONNECT_COUNT = 1;

    public static final int SEND_COUNT_SINGLE_CONNECTION = 100;

    public static void main(String[] args) throws Exception {

        //创建线程池
        ExecutorService service = Executors.newCachedThreadPool();

        SysNotice sysNotice = new SysNotice();

        RabbitMQUtil.getMQTTFactory();

        //sysNotice.setMessage("Hello Test ");
        //mqttServerUtil.publish(sysNotice,"*mqtt*","");
        for (int j = 0; j < CONNECT_COUNT; j++) {

            MqttServerUtil mqttServerUtil = new MqttServerUtil();
            MqttConnection mqttConnection = new MqttConnection();
            mqttServerUtil.init(String.valueOf(j) +" "+ UUID.randomUUID().toString(), mqttConnection);

            for (int i = 0; i < SEND_COUNT_SINGLE_CONNECTION; i++) {

                int finalJ = j;
                service.submit(() -> {
                    System.out.println("i : " + Thread.currentThread().getName() + "|线程名称：" + Thread.currentThread().getName());
                    try {
                        //Producer.producerExec();

                        sysNotice.setMessage("Hello Test "+Thread.currentThread().getName());
                        mqttServerUtil.publish(sysNotice,"*mqtt*",mqttConnection);
                        System.out.println("mqttConnection: "+ finalJ);
                        while (true){
                            Thread.sleep(200000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Thread.sleep(1000);
            }
            Thread.sleep(10000);
        }
    }
}