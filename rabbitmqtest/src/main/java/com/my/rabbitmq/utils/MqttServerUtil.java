package com.my.rabbitmq.utils;

import com.alibaba.fastjson.JSON;
import com.my.rabbitmq.entity.MqttConnection;
import com.my.rabbitmq.entity.SysNotice;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.omg.CORBA.portable.ApplicationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*@ApplicationScoped
@Slf4j
@Service*/
public class MqttServerUtil {

    //MqttConnection mqttConnection = new MqttConnection();

    private String host;

    private String clientId;

    private String mqttUserName;

    private String mqttPassword;

    private MqttClient mqttClient;

    private MqttTopic mqttTopic;

    public void publish(SysNotice msg, String topic,MqttConnection mqttConnection) throws MqttException, ApplicationException, IOException {

        if (isKeyInfoNull()) {
            //init(UUID.randomUUID().toString(), mqttConnection);
        }

        if (!mqttClient.isConnected()) {
            System.out.println("create connect=======================");
            connect();

        }
            System.out.println(mqttClient.isConnected()+"reuse connect-------------------------");


        String jsonStr = JSON.toJSONString(msg);

        MqttMessage message = new MqttMessage();

        message.setQos(0);
        message.setRetained(false);
        message.setPayload(jsonStr.getBytes(StandardCharsets.UTF_8));

        mqttClient.publish(topic, message);

//        mqttTopic = mqttClient.getTopic("jmc.notice.type.mqtt");
//        MqttDeliveryToken token = mqttTopic.publish(message);
//        token.waitForCompletion();

       /* if (mqttClient.isConnected()) {
            mqttClient.disconnect();
            System.out.println("disconnect**********************");
        }*/
        System.out.println("message is published completely! topic: " + topic + ", time: " + System.currentTimeMillis() + ",message:" + msg.getMessage());
        //log.info("message is published completely! topic: " + topic + ", time: " + System.currentTimeMillis());
    }

    private void connect() throws MqttException {

        MqttConnectOptions options = new MqttConnectOptions();

        options.setCleanSession(true);
        options.setUserName(mqttUserName);
        options.setPassword(mqttPassword.toCharArray());
        options.setConnectionTimeout(60);
        options.setKeepAliveInterval(600);
        options.setAutomaticReconnect(false);
        //options.setMaxReconnectDelay(60);

        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                // 连接丢失后，一般在这里面进行重连
                System.out.println("mqtt connection lost -> clientId: " + clientId + ", host: " + host + ", time: " + System.currentTimeMillis());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                System.out.println("message arrived topic : " + topic);
                /*log.info("message arrived topic : " + topic);
                log.info("message arrived Qos : " + message.getQos());
                log.info("message arrived content : " + new String(message.getPayload()));*/
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("mqtt delivery complete -> clientId: " + clientId + ", host: " + host + ", time: " + System.currentTimeMillis());
                //log.info("mqtt delivery complete -> clientId: " + clientId + ", host: " + host + ", time: " + System.currentTimeMillis());
            }
        });

        mqttClient.connect(options);
    }

    /**
     * host 从数据库查
     * 创建 MqttClient，并连接
     *
     * @throws MqttException e
     */
    public void init(String client,MqttConnection mqttConnection) throws MqttException, ApplicationException, FileNotFoundException , IOException {
        //log.info("mqtt start init");
        //  ->  tcp://127.0.0.1:1883
        mqttConnection.setHost("172.18.1.8:5671");
        this.host = "tcp://"+mqttConnection.getHost();
        //log.info("host url is " + this.host);
        this.clientId = "my_mqtt_fps_notice_client"+client;
        this.mqttUserName = "guest";
        this.mqttPassword = "guest";
        this.mqttClient = new MqttClient(host, clientId, new MemoryPersistence());

        System.out.println("mqtt connected start-> clientId: " + clientId + ", host: " + host + ", time: " + System.currentTimeMillis());
        //log.info("mqtt connected start-> clientId: " + clientId + ", host: " + host + ", time: " + System.currentTimeMillis());
        connect();
        System.out.println("create connect=======================");
        //log.info("mqtt connected success-> clientId: " + clientId + ", host: " + host + ", time: " + System.currentTimeMillis());
    }

    private boolean isKeyInfoNull() {
        if (host == null || host.isEmpty()) {
            return true;
        }

        if (clientId == null || clientId.isEmpty()) {
            return true;
        }

        return mqttClient == null;
    }


}
