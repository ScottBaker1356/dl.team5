package com.team5.dl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JavaJsonSerializationController {

    public static <T> T deserialize(String json, Class<T> tClass) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        T t = null;
        try {
            t = mapper.readValue(json, tClass);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize");
        }

        return t;
    }

    public static <T> String serialize(T t) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            String value = mapper.writeValueAsString(t);

            return value;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize");
        }

    }

}
