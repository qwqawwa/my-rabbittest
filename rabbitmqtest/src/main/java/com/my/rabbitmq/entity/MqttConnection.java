package com.my.rabbitmq.entity;

/**
 * @ClassName Connection
 * @Description TODO
 * @Author Cheng Liu
 * @Date 2022/8/22 21:24
 */
public class MqttConnection {

    String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public MqttConnection() {
        this.host = "localhost";
    }
}