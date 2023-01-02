package com.knowledgespike;


import com.knowledgespike.quotes.consumer.ConsumeUserRegistration;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ResisterUserPollConsoleApplication {

    public static void main(String[] args) throws IOException, TimeoutException {

        var factory = new ConnectionFactory();
        factory.setHost("localhost");

        var connection = factory.newConnection();

        var consumeUserRegistration = new ConsumeUserRegistration(connection);

        consumeUserRegistration.pollQueue();

        connection.close();

    }
}
