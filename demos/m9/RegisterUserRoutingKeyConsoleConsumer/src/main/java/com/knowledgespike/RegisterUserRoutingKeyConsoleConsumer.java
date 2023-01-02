package com.knowledgespike;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.consumer.ConsumeUserRegistrationWithRoutingKey;
import com.knowledgespike.quotes.messages.UserRegistrationMessage;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RegisterUserRoutingKeyConsoleConsumer {

    static DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody());

        var mapper = new ObjectMapper();
        var userRegistrationMessage = mapper.readValue(message, UserRegistrationMessage.class);

        System.out.println(" [*] Consumed message: " + userRegistrationMessage);
    };


    public static void main(String[] args) throws IOException, TimeoutException {
        var factory = new ConnectionFactory();
        factory.setHost("localhost");
        var connection = factory.newConnection();

        if (args.length == 0 || args[0].equals("1")) {

            var consumeUserRegistration = new ConsumeUserRegistrationWithRoutingKey(connection, Constants.RegisterUserWithExchangeQueue, Constants.RegisterUserWithExchangeRoutingKey);

            consumeUserRegistration.consumeMessage(deliverCallback);
        } else {
            var consumeUserRegistration = new ConsumeUserRegistrationWithRoutingKey(connection, Constants.RegisterUserWithExchangeQueue, Constants.RegisterUserWithExchangeAnotherRoutingKey);

            consumeUserRegistration.consumeMessage(deliverCallback);

        }
    }
}
