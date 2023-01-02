package com.knowledgespike.quotes.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgespike.quotes.JsonHelpers;
import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.messages.UserRegistrationMessage;
import com.rabbitmq.client.*;

import java.io.IOException;

public class ConsumeUserRegistration {

    private final Channel channel;

    public ConsumeUserRegistration(Connection connection) throws IOException {
        channel = connection.createChannel();

        channel.queueDeclare(Constants.RegisterUserRoutingKey, false, false, false, null);
    }

    public void pollQueue() {
        try {
            GetResponse response = null;
            while(response == null) {
                System.out.println(" [*] waiting ...");
                Thread.sleep(1000);
                response = channel.basicGet(Constants.RegisterUserRoutingKey, true);
            }

            var message = JsonHelpers.fromJson(response.getBody(), UserRegistrationMessage.class);

            System.out.println(" [*] Consumed message: " + message);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void pullMessage() {

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());

            var mapper = new ObjectMapper();
            var userRegistrationMessage = mapper.readValue(message, UserRegistrationMessage.class);

            System.out.println(" [*] Consumed: " + userRegistrationMessage);
        };

        try {
            channel.basicConsume(Constants.RegisterUserRoutingKey, true, deliverCallback, (CancelCallback) null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void pullMessageWithCallback(DeliverCallback deliverCallback) {

        try {
            channel.basicConsume(Constants.RegisterUserRoutingKey, true, deliverCallback, (CancelCallback) null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
