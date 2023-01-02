package com.knowledgespike.quotes.consumer;

import com.knowledgespike.quotes.constants.Constants;
import com.rabbitmq.client.*;

public class ConsumeQuoteMessage {

    private final Channel channel;
    private final String queueName;

    public ConsumeQuoteMessage(Connection connection) {
        try {
            channel = connection.createChannel();
            queueName = channel.queueDeclare().getQueue();
            channel.exchangeDeclare(Constants.QuotesExchange, BuiltinExchangeType.FANOUT);

            channel.queueBind(queueName, Constants.QuotesExchange, "");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void consumeMessage(DeliverCallback deliverCallback) {
        try {
            channel.basicConsume(queueName, true, deliverCallback, (CancelCallback) null);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

    }









}
