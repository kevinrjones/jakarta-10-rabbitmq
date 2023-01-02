package com.knowledgespike.quotes.consumer;

import com.knowledgespike.quotes.constants.Constants;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class ConsumeDlxMessage  {

    Channel channel;

    public ConsumeDlxMessage(Connection connection) throws  IOException {


        channel = connection.createChannel();
        channel.exchangeDeclare(Constants.DlxExchangeName,  BuiltinExchangeType.DIRECT);

        channel.queueDeclare(Constants.DlxQueueName, false, false, false, null);
        channel.queueBind(Constants.DlxQueueName, Constants.DlxExchangeName, Constants.DlxRoutingKey);

    }

    public void consumeMessage(DeliverCallback callback) {
        try {
            channel.basicConsume(Constants.DlxQueueName, true, callback, (t) -> {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }


}
