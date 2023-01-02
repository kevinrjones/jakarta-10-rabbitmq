package com.knowledgespike;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PublishRegisterUser {

    private Channel channel;


    public PublishRegisterUser(Channel channel) {
        this.channel = channel;
    }

    public boolean sendMessage(String message) {
        try {
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
