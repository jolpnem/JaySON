package ru.test.JaySON.Serializer;

public class NumberSerializer implements Serializer {
    @Override
    public String serialize(Object serializationObject) {
        return String.valueOf(serializationObject);
    }

    @Override
    public boolean supports(Object object) {
        return object instanceof Number;
    }
}
