package com.knowledgespike.quotes.producer;

import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.messages.UserRegistrationMessage;
import com.rabbitmq.client.Connection;

import static com.knowledgespike.quotes.JsonHelpers.toJson;

public class PublishRegisterUser {

    private Connection connection;

    public PublishRegisterUser(Connection connection) {
        this.connection = connection;
    }

    public boolean publishMessage(UserRegistrationMessage message) {
        try(var channel = connection.createChannel()) {
            var jsonFormattedMessage = toJson(message);
            channel.basicPublish(Constants.DefaultExchange, Constants.RegisterUserRoutingKey, null, jsonFormattedMessage);
            System.out.println(" [*] Published " + message);
        }
        catch (Exception e) {
            System.out.println(" [x] " + e.getMessage());
            return false;
        }

        return true;
    }
}
