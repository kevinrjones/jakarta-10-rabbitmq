package com.knowledgespike.quotes.producer;

import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.messages.CarDetailsMessage;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;

import static com.knowledgespike.quotes.JsonHelpers.toJson;

public class PublishFanOutPriceQuotes {

    private Connection connection;

    public PublishFanOutPriceQuotes(Connection connection) {
        this.connection = connection;
    }

    public boolean publishMessage(CarDetailsMessage message) {

        try(var channel = connection.createChannel()) {
            var jsonFormattedMessage = toJson(message);

            channel.exchangeDeclare(Constants.QuotesExchange, BuiltinExchangeType.FANOUT);

            channel.basicPublish(Constants.QuotesExchange, "", null, jsonFormattedMessage);

            System.out.println(" [*] Published " + message);

        } catch (Exception e) {
            System.out.println(" [x] " + e.getMessage());
            return false;
        }

        return true;
    }
}












