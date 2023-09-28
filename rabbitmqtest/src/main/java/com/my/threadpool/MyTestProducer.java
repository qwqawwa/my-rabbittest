package com.my.threadpool;

import com.my.rabbitmq.service.ProducerTestMultiConnect;
import org.junit.Test;

/**
 * @ClassName MyTestProducer
 * @Description TODO
 * @Author Cheng Liu
 * @Date 06/12/2022 9:02 PM
 */
public class MyTestProducer {
    public static void main(String[] args) throws Exception {
        //长连接，一个connection
        //ProducerTest.producerExec();

        //多连接，多个connection
        ProducerTestMultiConnect.producerExec();

    }
    @Test
    //多连接，多个connection
    public void producerTestMultiConnect() throws Exception {
        ProducerTestMultiConnect.producerExec();
    }
}