package ru.test.JaySON.Serializer;

public class StringSerializer implements Serializer {
    @Override
    public String serialize(Object serializationObject) {
        return "\"" + serializationObject + "\"";
    }

    @Override
    public boolean supports(Object object) {
        return object instanceof String;
    }
}
