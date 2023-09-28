package com.example.springbootmyrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName SendMsgController
 * @Description 发送消息控制层
 * @Author Cheng Liu
 * @Date 06/09/2022 10:55 PM
 */
@Slf4j
@RequestMapping("/ttl")
@RestController
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final int CONNECT_COUNT = 1000;
    public static final int COUNT = 2000;

    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) throws InterruptedException {
        // Consumer.consumerExec();
        for (int j = 0; j < CONNECT_COUNT; j++) {

            //创建线程池
            ExecutorService service = Executors.newCachedThreadPool();
            for (int i = 0; i < COUNT; i++) {
                int finalI = i;
                service.submit(() -> {
                    System.out.println("i : " + finalI + "|线程名称：" + Thread.currentThread().getName());
                    try {
                        rabbitTemplate.convertAndSend("X","Key_X","消息："+message);
                        log.info("hello message: " + message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            Thread.sleep(100);





        }

    }
}