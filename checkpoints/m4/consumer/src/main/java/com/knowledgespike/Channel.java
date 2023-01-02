package com.knowledgespike;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Channel {
    void basicPublish(String exchange, String queue, Object properties, byte[] message) throws IOException, TimeoutException;

    void basicConsume(String queueName, boolean autoAck, DeliverCallback callback, CancelCallback cancelCallback) throws IOException;
}
