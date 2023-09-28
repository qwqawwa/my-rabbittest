package com.example.springbootmyrabbitmq;

import com.example.springbootmyrabbitmq.service.SendMsgService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootMyrabbitmqApplicationTests {

    @Test
    void contextLoads() {
        SendMsgService sendMsgService = new SendMsgService();
        sendMsgService.send4Test();
    }

}
