package com.knowledgespike.quotes.producer;

import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.messages.InsuranceMessage;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;

import java.util.Map;

import static com.knowledgespike.quotes.JsonHelpers.toJson;

public class HeaderRoutingPriceQuotes {

    private final Connection connection;

    public HeaderRoutingPriceQuotes(Connection connection) {
        this.connection = connection;
    }

    public boolean publishMessage(InsuranceMessage insurance, Map<String, Object> headers) {
        try(var channel = connection.createChannel()) {
            channel.exchangeDeclare(Constants.HeadersExchange, BuiltinExchangeType.HEADERS);

            var messageAsJson = toJson(insurance);
            var properties = new AMQP.BasicProperties.Builder()
                    .headers(headers)
                            .build();

            channel.basicPublish(Constants.HeadersExchange, "", properties, messageAsJson);

            System.out.println(" [*] published: " + insurance);


        }catch (Exception e) {
            System.out.println(" [x] Unable to publish message: " + e.getMessage());
            return false;
        }






        return true;
    }
}
