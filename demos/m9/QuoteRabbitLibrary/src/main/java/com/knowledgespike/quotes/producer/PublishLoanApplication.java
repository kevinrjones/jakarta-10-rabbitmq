package com.knowledgespike.quotes.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.messages.LoanApplicationMessage;
import com.knowledgespike.quotes.messages.LoanResponseMessage;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Connection;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.knowledgespike.quotes.JsonHelpers.toJson;

public class PublishLoanApplication {

    private Connection connection;

    public PublishLoanApplication(Connection connection) {
        this.connection = connection;
    }

    public String publishMessage(LoanApplicationMessage message) {
        try(var channel = connection.createChannel()) {
            var messageAsJson = toJson(message);

            channel.queueDeclare(Constants.LoanApplicationQueue, false, false, true, null);
            var replyTo = channel.queueDeclare().getQueue();
            var correlationId = UUID.randomUUID().toString();

            var properties = new AMQP.BasicProperties.Builder()
                    .correlationId(correlationId)
                            .replyTo(replyTo)
                                    .build();

            channel.basicPublish("", Constants.LoanApplicationQueue, properties, messageAsJson);

            System.out.println(" [*] Published message: " + message + " correlationId: " + correlationId + " replyTo: " + replyTo);

            final BlockingQueue<LoanResponseMessage> response = new ArrayBlockingQueue<>(1);

            channel.basicConsume(replyTo, true, (consumerTag, delivery) -> {
                var received = delivery.getBody();

                var mapper = new ObjectMapper();
                var responseMessage = mapper.readValue(received, LoanResponseMessage.class);

                var corrId = delivery.getProperties().getCorrelationId();

                System.out.println(" [*] Received message: " + responseMessage + " with correlationID: " + corrId);

                response.offer(responseMessage);

            }, (CancelCallback) null);
            var msg = response.poll(2, TimeUnit.SECONDS);
            System.out.println(" [*] Received message: " + msg);

            if(msg != null)
                return msg.toString();
            return "";

        } catch (Exception e) {
            System.out.println(" [x] Unable to publish message: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}











