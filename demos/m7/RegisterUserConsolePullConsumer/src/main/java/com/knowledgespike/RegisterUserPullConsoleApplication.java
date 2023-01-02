package com.knowledgespike;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgespike.quotes.consumer.ConsumeUserRegistration;
import com.knowledgespike.quotes.messages.UserRegistrationMessage;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RegisterUserPullConsoleApplication {

    static DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody());

        var mapper = new ObjectMapper();
        var userRegistrationMessage = mapper.readValue(message, UserRegistrationMessage.class);

        System.out.println(" [*] Consumed in main: " + userRegistrationMessage);
    };



    public static void main(String[] args) throws IOException, TimeoutException {
        var factory = new ConnectionFactory();
        factory.setHost("localhost");
        var connection = factory.newConnection();

        var consumeUserRegistration = new ConsumeUserRegistration(connection);

        System.out.println(" [*] Waiting to consume messages");
//        consumeUserRegistration.pullMessage();

        consumeUserRegistration.pullMessageWithCallback(deliverCallback);
    }
}
