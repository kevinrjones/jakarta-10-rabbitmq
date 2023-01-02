package com.knowledgespike;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgespike.quotes.consumer.ConsumeQuoteMessage;
import com.knowledgespike.quotes.messages.CarDetailsMessage;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class FanOutPubSubConsoleApplication {

    static DeliverCallback callback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();

        var carDetailsMessage = mapper.readValue(message, CarDetailsMessage.class);

        System.out.println(" [*] Consume quote message: " + carDetailsMessage);
    };

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        var connection = factory.newConnection();

        var consumer = new ConsumeQuoteMessage(connection);

        consumer.consumeMessage(callback);
    }
}
