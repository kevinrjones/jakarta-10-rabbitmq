package com.knowledgespike.quotes.consumer;

import com.rabbitmq.client.*;

public class ConsumeTopicMessage {
    private final Channel channel;
    private final String queueName;


    public ConsumeTopicMessage(Connection connection, String exchangeName, String routingKey)  {
        try {
            channel = connection.createChannel();
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, exchangeName, routingKey);
        } catch (Exception e) {
            System.out.println(" [x] Unable to create channel");
            throw new RuntimeException(e);
        }
    }


    public void consumeMessage(DeliverCallback callback) {
        try {
            channel.basicConsume(queueName, true, callback, (CancelCallback)null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

}
