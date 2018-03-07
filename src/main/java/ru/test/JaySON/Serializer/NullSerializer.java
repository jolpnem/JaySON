package ru.test.JaySON.Serializer;

public class NullSerializer implements Serializer {
    @Override
    public String serialize(Object serializationObject) {
        return "null";
    }

    @Override
    public boolean supports(Object object) {
        return object == null;
    }
}
