package com.my.threadpool;

import com.my.rabbitmq.service.Consumer;

/**
 * @ClassName MyTest
 * @Description TODO
 * @Author Cheng Liu
 * @Date 2022/8/19 14:57
 */
public class MyTest {


        public static void main(String[] args) throws Exception {

            Consumer.consumerExec();
            //RabbitMQUtil.getConnection();
            /*for (int i = 0; i < 9; i++) {
                Producer.producerExec();
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

        //Producer.producerExec();
        //Consumer.consumerExec();

    }
