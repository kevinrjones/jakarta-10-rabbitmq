package com.knowledgespike.quotes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

public class JsonHelpers {
    public static <T> byte[] toJson(T message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(message).getBytes(StandardCharsets.UTF_8);
    }
    public static <T> T fromJson(byte[] message, Class clazz) throws JsonProcessingException {
        var json = new String(message);
        ObjectMapper mapper = new ObjectMapper();
        return (T)mapper.readValue(json, clazz);
    }

}
