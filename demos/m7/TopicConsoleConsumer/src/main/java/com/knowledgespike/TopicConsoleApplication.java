package com.knowledgespike;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.consumer.ConsumeTopicMessage;
import com.knowledgespike.quotes.messages.CarDetailsMessage;
import com.knowledgespike.quotes.messages.HomeDetailsMessage;
import com.knowledgespike.quotes.messages.InsuranceMessage;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class TopicConsoleApplication {

    static DeliverCallback carQuoteCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();

        var carDetailsMessage = mapper.readValue(message, CarDetailsMessage.class);

        System.out.println(" [*] consumed car details message: " + carDetailsMessage);

    };

    static DeliverCallback homeQuoteCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();

        var homeDetailsMessage = mapper.readValue(message, HomeDetailsMessage.class);

        System.out.println(" [*] consumed home details message: " + homeDetailsMessage);

    };

    static DeliverCallback allQuoteCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();

        var insuranceMessage = mapper.readValue(message, InsuranceMessage.class);

        System.out.println(" [*] consumed insurance message: " + insuranceMessage);

    };

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        var connection = factory.newConnection();

        var consumer = new ConsumeTopicMessage(connection, Constants.TopicExchangeName, "quotes.car");
        System.out.println(" [*] Waiting for car quotes");
        consumer.consumeMessage(carQuoteCallback);

        consumer = new ConsumeTopicMessage(connection, Constants.TopicExchangeName, "quotes.home");
        System.out.println(" [*] Waiting for home quotes");
        consumer.consumeMessage(homeQuoteCallback);

        consumer = new ConsumeTopicMessage(connection, Constants.TopicExchangeName, "quotes.*");
        System.out.println(" [*] Waiting for all quotes");
        consumer.consumeMessage(allQuoteCallback);

    }
}
