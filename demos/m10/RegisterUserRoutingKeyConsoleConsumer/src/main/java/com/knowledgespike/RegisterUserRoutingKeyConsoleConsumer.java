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
    static ConsumeUserRegistrationWithRoutingKey consumeUserRegistration1;
    static ConsumeUserRegistrationWithRoutingKey consumeUserRegistration2;

    static DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody());

        var mapper = new ObjectMapper();
        var userRegistrationMessage = mapper.readValue(message, UserRegistrationMessage.class);

        System.out.println(" [*] Consumed message: " + userRegistrationMessage);
        consumeUserRegistration1.acknowledge(delivery.getEnvelope().getDeliveryTag());
        consumeUserRegistration2.acknowledge(delivery.getEnvelope().getDeliveryTag());
    };


    public static void main(String[] args) throws IOException, TimeoutException {
        var factory = new ConnectionFactory();
        factory.setHost("localhost");
        var connection = factory.newConnection();


        consumeUserRegistration1 = new ConsumeUserRegistrationWithRoutingKey(connection,
                Constants.RegisterUserWithExchangeQueueD,
                Constants.RegisterUserWithExchangeRoutingKey,
                true);

        consumeUserRegistration2 = new ConsumeUserRegistrationWithRoutingKey(connection,
                Constants.RegisterUserWithExchangeQueueND,
                Constants.RegisterUserWithExchangeAnotherRoutingKey,
                false);

        consumeUserRegistration1.consumeMessage(deliverCallback);
        consumeUserRegistration2.consumeMessage(deliverCallback);

        System.out.println(" [*] Waiting for messages");

    }
}

