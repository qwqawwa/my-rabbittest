package com.my.rabbitmq.utils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
/**
 * @ClassName MQTTCallback
 * @Description TODO
 * @Author Cheng Liu
 * @Date 2022/8/23 0:17
 */


          /**
    * @author zuixiaoyao
    * 该回调需要实现MqttCallback的接口
    */
          public class MQTTCallback implements MqttCallback{
 //    该方法将在与服务器的连接断开时调用
            @Override
     public void connectionLost(Throwable cause) {
                     // TODO Auto-generated method stub
                cause.printStackTrace();
                    System.out.println("进入connectionLost方法，可以在此重新连接");
                 }
 // 到消息到达服务器是调用此方法
             @Override
     public void messageArrived(String topic, MqttMessage message) throws Exception {
                     // TODO Auto-generated method stub
                     System.out.println("进入messageArrived方法---->："+"\n主题："+topic+"\n服务保障："+message.getQos()+
                                     "\n消息id："+message.getId()+"\n消息体："+new String(message.getPayload()));
                 }
 //    该方法在消息发送完成时调用，并且已经收到所有确认时调用此方法
         //    接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。
             @Override
     public void deliveryComplete(IMqttDeliveryToken token) {
                     // TODO Auto-generated method stub
                     try {
                            System.out.println("进入deliveryComplete方法，消息发送是否完成："+token.isComplete()+"/n消息id："+token.getMessageId()+
                                            "/n消息服务："+token.getMessage().getQos()+"\n消息内容："+new String(token.getMessage().getPayload()));
                         } catch (MqttException e) {
                             // TODO Auto-generated catch block
                             e.printStackTrace();
                       }
                }

        }

