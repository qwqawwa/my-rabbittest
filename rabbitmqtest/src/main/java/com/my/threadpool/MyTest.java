package com.my.threadpool;

import com.my.rabbitmq.service.Consumer;
import com.my.rabbitmq.service.Producer;
import com.my.rabbitmq.utils.RabbitMQConstant;
import com.my.rabbitmq.utils.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @ClassName MyTest
 * @Description TODO
 * @Author Cheng Liu
 * @Date 2022/8/19 14:57
 */
public class MyTest {

        public static final int CONNECT_COUNT = 1;
        public static final int COUNT = 200;

        public static void main(String[] args) throws Exception {

           // Producer.producerExec();
           // Consumer.consumerExec();
            String msg = myTestQueueGetMsg();
            System.out.println(msg);
         /*  // Consumer.consumerExec();
            for (int j = 0; j < CONNECT_COUNT; j++) {
            //创建线程池
            ExecutorService service = Executors.newCachedThreadPool();
            for (int i = 0; i < COUNT; i++) {
                int finalI = i;
                service.submit(() -> {
                    System.out.println("i : " + finalI + "|线程名称：" + Thread.currentThread().getName());
                    try {
                        RabbitMQUtil.getConnection();
                        Producer.producerExec();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

                Thread.sleep(100);
        }*/

   /*         MqttUtil2 service = MqttUtil2.builder()
                    .host("tcp://172.18.1.10:1883")
                    .userName("guest")
                    .passWord("guest")
                    .clientId("myclientid_10002")
                    //test
                    .defaultTopic("*mqtt*")
                    //.defaultTopic("MIDDOL-TEST")
                    //test
            // .cleanSession(true).build();
                    .cleanSession(false).build();

            Thread.sleep(3000L);
            service.sendMessage("这是java后端发送的消息");

            // service.closeClient(false);*/
          /*  for(int i=0; i<2;i++){
                SysNotice sysNotice = new SysNotice();
                sysNotice.setMessage("Hello Test "+i);
                MqttServerUtil mqttServerUtil = new MqttServerUtil();
                mqttServerUtil.publish(sysNotice,"*mqtt*");
            }*/

        }

        public static String myTestQueueGetMsg() throws Exception {
            Channel channel = RabbitMQUtil.getChannel();
            RabbitMQUtil.initQueueDeclare(channel,RabbitMQConstant.MY_TEST_QUEUE_NAME);
            Consumer.consumerExec(channel,RabbitMQConstant.MY_TEST_QUEUE_NAME);
            return "消费成功";
        }

        public static String producerClient() throws Exception {
            Connection connection = RabbitMQUtil.getConnection();
            Channel channel = RabbitMQUtil.getChannelByConn(connection);
            Producer.producerExec(channel,RabbitMQConstant.MY_TEST_QUEUE_NAME,"push test");
            return "推送成功";
        }
}

        //Producer.producerExec();
        //Consumer.consumerExec();


