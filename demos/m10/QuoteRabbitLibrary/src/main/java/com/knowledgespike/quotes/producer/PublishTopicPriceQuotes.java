package com.knowledgespike.quotes.producer;

import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.messages.InsuranceMessage;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import static com.knowledgespike.quotes.JsonHelpers.toJson;

public class PublishTopicPriceQuotes {

    private final Connection connection;

    public PublishTopicPriceQuotes(Connection connection) {
        this.connection = connection;
    }

    public boolean publishMessage(InsuranceMessage insurance, String routingKey) {
        try(var channel = connection.createChannel()) {
            channel.exchangeDeclare(Constants.TopicExchangeName, BuiltinExchangeType.TOPIC);
            var messageAsJson = toJson(insurance);

            channel.basicPublish(Constants.TopicExchangeName, routingKey, null, messageAsJson);

            System.out.println(" [*] published: " + insurance);
        }catch (Exception e) {
            System.out.println(" [x] Unable to publish message: " + e.getMessage());
            return false;
        }






        return true;
    }
}
