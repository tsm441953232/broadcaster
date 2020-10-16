package com.tsm.broadcaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class MqMessage extends Message{
    @JsonIgnore
    private String routingKey;
    @JsonIgnore
    private String exchange;

    public MqMessage(){};

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public String toString() {
        return "MqMessage{" +
                "routingKey='" + routingKey + '\'' +
                ", exchange='" + exchange + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MqMessage mqMessage = (MqMessage) o;
        return Objects.equals(routingKey, mqMessage.routingKey) &&
                Objects.equals(exchange, mqMessage.exchange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routingKey, exchange);
    }
}
