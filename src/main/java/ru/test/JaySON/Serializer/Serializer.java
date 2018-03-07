package ru.test.JaySON.Serializer;

public interface Serializer {
    String serialize(Object serializationObject);
    boolean supports(Object object);
}
