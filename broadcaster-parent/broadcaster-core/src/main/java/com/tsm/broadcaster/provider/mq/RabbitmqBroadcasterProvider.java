package com.tsm.broadcaster.provider.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tsm.broadcaster.BroadcasterProvider;
import com.tsm.broadcaster.entity.MqMessage;
import com.tsm.broadcaster.exception.BroadcastRuntimeException;
import com.tsm.broadcaster.exception.NotifyException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitmqBroadcasterProvider implements BroadcasterProvider<MqMessage> {
    private Map<String, Object> params = null;
    private final ConnectionFactory factory = new ConnectionFactory();
    private Connection connection = null;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RabbitmqBroadcasterProvider() {
    }

    public void setParams(Map<String, Object> params) {
        Preconditions.checkNotNull(params, "params must not be null");
        Preconditions.checkNotNull(params.get("host"), "host must not be null");
        Preconditions.checkNotNull(params.get("port"), "port must not be null");
        Preconditions.checkNotNull(params.get("username"), "username must not be null");
        Preconditions.checkNotNull(params.get("password"), "password must not be null");
        this.params = params;
        this.initRabbitmq();
    }

    private void initRabbitmq() {
        this.factory.setUsername(this.params.get("username").toString());
        this.factory.setPassword(this.params.get("password").toString());
        this.factory.setHost(this.params.get("host").toString());
        this.factory.setPort(Integer.parseInt(this.params.get("port").toString()));

        try {
            this.newConnection();
        } catch (Exception var2) {
            throw new BroadcastRuntimeException("创建rabbitmq连接错误！", var2);
        }
    }

    private void newConnection() throws IOException, TimeoutException {
        this.connection = this.factory.newConnection();
    }

    public void notify(MqMessage message) throws NotifyException, IOException, TimeoutException {
        this.prepare();
        Channel channel = null;

        try {
            channel = this.connection.createChannel();
            channel.basicPublish(this.getExchange(message), this.getRoutingKey(message), (AMQP.BasicProperties)null, this.getMessageBodyBytes(message));
        } catch (Exception var7) {
            throw new NotifyException("无法重新创建channel！", var7);
        } finally {
            channel.close();
        }

    }

    private void prepare() throws IOException, TimeoutException {
        if (this.connection == null || !this.connection.isOpen()) {
            this.newConnection();
        }

    }

    private String getExchange(MqMessage message) {
        String exchange = message.getExchange();
        if (exchange == null || exchange.trim().length() == 0) {
            exchange = "";
        }

        return exchange;
    }

    private String getRoutingKey(MqMessage message) throws NotifyException {
        String routingKey = message.getRoutingKey();
        if (routingKey != null && routingKey.trim().length() != 0) {
            return routingKey;
        } else {
            throw new NotifyException("传入的消息中没有定义routingKey");
        }
    }

    private byte[] getMessageBodyBytes(MqMessage message) throws NotifyException {
        try {
            return this.objectMapper.writeValueAsString(message).getBytes();
        } catch (JsonProcessingException var3) {
            throw new NotifyException("传入的消息对象无法完成序列化！", var3);
        }
    }
}
