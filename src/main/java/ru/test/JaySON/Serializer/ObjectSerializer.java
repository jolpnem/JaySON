package ru.test.JaySON.Serializer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectSerializer implements Serializer, SerializerAware {
    private Serializer serializer;

    @Override
    public String serialize(Object serializationObject) {
        Map<String, Object> fieldNamesAndValues = new HashMap<>();

        for (Field field : serializationObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            String fieldName = field.getName();

            try {
                fieldNamesAndValues.put(fieldName, field.get(serializationObject));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return serializer.serialize(fieldNamesAndValues);
    }

    @Override
    public boolean supports(Object object) {
        return true;
    }

    @Override
    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }
}
