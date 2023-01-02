package com.knowledgespike;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgespike.quotes.consumer.ConsumeLoanMessage;
import com.knowledgespike.quotes.messages.LoanApplicationMessage;
import com.knowledgespike.quotes.messages.LoanResponseMessage;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RpcConsoleApplication {

    static DeliverCallback callback = (consumerTag, delivery) -> {
        try {
            var message = new String(delivery.getBody());
            var mapper = new ObjectMapper();

            var loanApplicationMessage = mapper.readValue(message, LoanApplicationMessage.class);

            processMessage(loanApplicationMessage);

            sendReplyToOriginalPublisher(delivery.getProperties().getReplyTo(), delivery.getProperties().getCorrelationId()
            , delivery.getEnvelope().getDeliveryTag());

        } catch (Exception e) {
            System.out.println(" [x] " + e.getMessage());
        }
    };

    private static boolean sendReplyToOriginalPublisher(String replyTo, String correlationId, long deliveryTag) {
        var loanResponseMessage = new LoanResponseMessage(true);
        return consumer.reply(loanResponseMessage, replyTo, correlationId, deliveryTag);
    }

    private static void processMessage(LoanApplicationMessage loanApplicationMessage) throws InterruptedException {
        Thread.sleep(1000);
//throw new RuntimeException();
        System.out.println(" [*] Consumed: " + loanApplicationMessage);
    }

    private static ConsumeLoanMessage consumer;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        var connection = factory.newConnection("app:api:client");

        consumer = new ConsumeLoanMessage(connection);
        consumer.consumeMessage(callback);
    }
}
