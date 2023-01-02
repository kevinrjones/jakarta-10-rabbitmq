package com.knowledgespike;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.consumer.ConsumeHeaderQuotesMessage;
import com.knowledgespike.quotes.messages.CarDetailsMessage;
import com.knowledgespike.quotes.messages.HomeDetailsMessage;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class HeadersConsoleApplication {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        var connection = factory.newConnection("app:api:client");

        try {

            Map<String, Object> headers = new HashMap<>();
            headers.put("x-match", "any");
            headers.put("insurance", "car");
            headers.put("vehicle", "motorbike");

            var headerQuotesMessage = new ConsumeHeaderQuotesMessage(connection, Constants.HeadersExchange, Constants.HeadersCarQueue, headers);

            System.out.println(" [*] Waiting for car messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [*] Received '" + message + "' with tag '" + consumerTag + "'");
                ObjectMapper mapper = new ObjectMapper();

                CarDetailsMessage carDetailsMessage = mapper.readValue(message, CarDetailsMessage.class);

                System.out.println(" [x] Car quote message: " + carDetailsMessage);

            };

            headerQuotesMessage.consumeMessage(deliverCallback);
        } catch (Exception e) {
            System.out.println(" [x] Failed to process message" + e.getMessage());
        }







        try {
            Map<String, Object> headers = new HashMap<>();
            headers.put("x-match", "all");
            headers.put("insurance", "buildings");
            headers.put("type", "business");

            var headerQuotesMessage = new ConsumeHeaderQuotesMessage(connection, Constants.HeadersExchange,  Constants.HeadersHomeQueue, headers);


            System.out.println(" [*] Waiting for home messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println(" [x] Received '" + message + "' with tag '" + consumerTag + "'");
                    ObjectMapper mapper = new ObjectMapper();

                    HomeDetailsMessage homeDetailsMessage = mapper.readValue(message, HomeDetailsMessage.class);

                    System.out.println(" [x] Home quote message: " + homeDetailsMessage);
            };

            headerQuotesMessage.consumeMessage(deliverCallback);
        } catch (Exception e) {
            System.out.println("Failed to process message" + e.getMessage());
        }
    }
}
