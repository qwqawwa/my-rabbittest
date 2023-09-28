package com.example.springbootmyrabbitmq.service;

import com.example.springbootmyrabbitmq.dto.InfoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName SendMsgService
 * @Description TODO
 * @Author Cheng Liu
 * @Date 17/11/2022 3:57 PM
 */
@Service
public class SendMsgService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    String exchange = "abc";
    String queue = "abc.testQueue";

    public void send4Test() {
        //自己的业务逻辑...
        InfoDTO dto = new InfoDTO();
        dto.setId("123");
        dto.setName("Jack");
        rabbitTemplate.convertAndSend(exchange, queue, dto);

    }
}