package com.knowledgespike.quotes.consumer;

import com.knowledgespike.quotes.constants.Constants;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DlxConsumeUserRegistration {


    Channel channel;

    public DlxConsumeUserRegistration(Connection connection) throws IOException {

        channel = connection.createChannel();

        channel.exchangeDeclare(Constants.DlxExchangeName, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(Constants.DlxQueueName, false, false, false, null);
        channel.queueBind(Constants.DlxQueueName, Constants.DlxExchangeName, Constants.DlxRoutingKey);

        var args = new HashMap<String, Object>();
        args.put("x-dead-letter-exchange", Constants.DlxExchangeName);
        args.put("x-dead-letter-routing-key", Constants.DlxRoutingKey);


        channel.queueDeclare(Constants.RegisterUserWithExchangeQueue, false, false, false, args);
        channel.queueBind(Constants.RegisterUserWithExchangeQueue, Constants.DirectExchange, Constants.RegisterUserWithExchangeRoutingKey);
    }

    public void consumeMessage(DeliverCallback callback) {
        try {
            channel.basicConsume(Constants.RegisterUserWithExchangeQueue, false, callback, (CancelCallback) null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

    public void acknowledgeMessage(long messageTag) {
        try {
            System.out.println(" [*] Acknowledge message");
            channel.basicAck(messageTag, false);
        } catch (Exception e) {
            System.out.println(" [x] Unable to acknowledge message" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void nAcknowledgeMessage(long messageTag) {
        try {
            System.out.println(" [*] Non-Acknowledge message");
            channel.basicNack(messageTag, false, false);
        } catch (Exception e) {
            System.out.println(" [*] Unable to nAcknowledge message" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
