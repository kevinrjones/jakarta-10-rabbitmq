package com.knowledgespike.quotes.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgespike.quotes.constants.Constants;
import com.knowledgespike.quotes.messages.UserRegistrationMessage;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PublishRegisterUserDlx {

    private final static Map<String, Object> arguments = null;
    private final Connection connection;

    public PublishRegisterUserDlx(Connection connection) {
        this.connection = connection;
    }


    public boolean publishMessage(UserRegistrationMessage message) {

        try (var channel = connection.createChannel()) {
            channel.exchangeDeclare(Constants.DlxExchangeName, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(Constants.DlxQueueName, false, false, false, null);
            channel.queueBind(Constants.DlxQueueName, Constants.DlxExchangeName, Constants.DlxRoutingKey);

            var args = new HashMap<String, Object>();
            args.put("x-dead-letter-exchange", Constants.DlxExchangeName);
            args.put("x-dead-letter-routing-key", Constants.DlxRoutingKey);


            channel.exchangeDeclare(Constants.DirectExchange, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(Constants.RegisterUserWithExchangeQueue, false, false, false, args);
            channel.queueBind(Constants.RegisterUserWithExchangeQueue, Constants.DirectExchange, Constants.RegisterUserWithExchangeRoutingKey);

            ObjectMapper mapper = new ObjectMapper();
            String userRegJson = mapper.writeValueAsString(message);

            channel.basicPublish(Constants.DirectExchange, Constants.RegisterUserWithExchangeRoutingKey, null, userRegJson.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [*] Published " + message );
            return true;
        } catch (Exception e) {
            System.out.println(" [x] Unable to publish message" + e.getMessage());
            return false;
        }
    }

}
