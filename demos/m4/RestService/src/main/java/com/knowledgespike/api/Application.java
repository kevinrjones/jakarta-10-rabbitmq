package com.knowledgespike.api;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.javalin.Javalin;

public class Application {

    static Connection connection;

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7777);
        try {
            var connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");

            connection = connectionFactory.newConnection("app:rest");

            app.post("/users", UserController.registerUser);
        } catch (Exception e) {
            System.out.println(" [x] Unable to start connection to RabbitMQ");
            System.out.println(e.getCause().getMessage());
            app.stop();
        }
    }
}



