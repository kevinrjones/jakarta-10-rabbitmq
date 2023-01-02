package com.knowledgespike.quotes.consumer;

import com.knowledgespike.quotes.constants.Constants;
import com.rabbitmq.client.*;

public class ConsumeUserRegistrationWithRoutingKey {
    private final Channel channel;
    private String queueName;

    public ConsumeUserRegistrationWithRoutingKey(Connection connection, String queueName, String routingKey, boolean durable) {
        this.queueName = queueName;
        try{
            channel = connection.createChannel();
            channel.exchangeDeclare(Constants.DirectExchange, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(queueName, durable,  false, false, null);
            channel.queueBind(queueName, Constants.DirectExchange, routingKey);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void consumeMessage(DeliverCallback callback) {
        try{
            channel.basicConsume(queueName, false, callback, (CancelCallback) null);
        } catch (Exception e) {
            System.out.println(" [x] Unable to consume message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void acknowledge(long deliveryTag) {
        try{
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            System.out.println("Unable to acknowldge message: " + deliveryTag + " Exception: " + e.getMessage());
        }
    }
}
