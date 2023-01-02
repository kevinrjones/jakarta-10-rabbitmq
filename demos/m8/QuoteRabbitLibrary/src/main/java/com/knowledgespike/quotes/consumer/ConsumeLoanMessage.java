package com.knowledgespike.quotes.consumer;

import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.messages.LoanResponseMessage;
import com.rabbitmq.client.*;

import static com.knowledgespike.quotes.JsonHelpers.toJson;

public class ConsumeLoanMessage {
    private final Channel channel;

    public ConsumeLoanMessage(Connection connection) {
        try {
            channel = connection.createChannel();
            channel.queueDeclare(Constants.LoanApplicationQueue, false, false, true, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void consumeMessage(DeliverCallback callback) {
        try {
            channel.basicConsume(Constants.LoanApplicationQueue, false, callback, (CancelCallback) null);
        }catch (Exception e) {
            System.out.println(" [x] Unable to consume message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean reply(LoanResponseMessage loanResponseMessage, String replyTo, String correlationId, long deliveryTag) {
        try {
            var jsonMessage = toJson(loanResponseMessage);

            var props = new AMQP.BasicProperties.Builder()
                    .correlationId(correlationId)
                            .build();

            System.out.println(" [*] About to reply");

            channel.basicPublish("", replyTo, props, jsonMessage);

            channel.basicAck(deliveryTag, false);
            return true;
        } catch (Exception e) {
            System.out.println(" [x] Unable to reply: " + e.getMessage());
            return false;
        }

    }
}















