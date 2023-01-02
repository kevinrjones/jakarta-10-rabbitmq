package com.knowledgespike.quotes.producer;

import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.messages.UserRegistrationMessage;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;

import static com.knowledgespike.quotes.JsonHelpers.toJson;

public class PublishRegisterUserWithExchange {
    private Connection connection;

    public PublishRegisterUserWithExchange(Connection connection) {
        this.connection = connection;
    }

    public boolean publishMessage(UserRegistrationMessage message) {
        try(var channel = connection.createChannel()) {
            var jsonFormattedMessage = toJson(message);

            channel.exchangeDeclare(Constants.DirectExchange, BuiltinExchangeType.DIRECT);

            channel.basicPublish(Constants.DirectExchange, Constants.RegisterUserWithExchangeRoutingKey, null, jsonFormattedMessage);

            System.out.println(" [*] Published (1): " + message);

            channel.basicPublish(Constants.DirectExchange, Constants.RegisterUserWithExchangeAnotherRoutingKey, null, jsonFormattedMessage);

            System.out.println(" [*] Published (2): " + message);
        } catch (Exception e) {

            System.out.println(" [x] Failed to published: " + e.getMessage());

            return false;
        }
        return true;
    }
}
