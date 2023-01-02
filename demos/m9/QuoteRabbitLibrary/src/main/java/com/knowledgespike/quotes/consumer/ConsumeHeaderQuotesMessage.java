package com.knowledgespike.quotes.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;

public class ConsumeHeaderQuotesMessage {

    String queueName;
    Channel channel;

    public ConsumeHeaderQuotesMessage(Connection connection, String exchangeName, String queueName, Map<String, Object> headers)  {
        try {
            this.queueName = queueName;
            channel = connection.createChannel();
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.HEADERS);
            channel.queueDeclare(queueName, false, false, true, null);
            channel.queueBind(queueName, exchangeName, "", headers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void consumeMessage(DeliverCallback callback) {
        try {
            channel.basicConsume(queueName, true, callback, (CancelCallback) null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

}
