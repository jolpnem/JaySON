package ru.test.JaySON;

import java.lang.reflect.Field;
import java.util.*;

@SuppressWarnings("WeakerAccess")
public class JaySON {
    public String serialize(Object serializationObject) {
        if (serializationObject == null) {
            return serializeNull();
        }
        else if (serializationObject instanceof Character) {
            return serializeCharacter((Character) serializationObject);
        }
        else if (serializationObject instanceof String) {
            return serializeString((String) serializationObject);
        }
        else if (serializationObject instanceof Number) {
            return serializeNumber((Number) serializationObject);
        }
        else if (serializationObject instanceof List) {
            return serializeCollection((Collection) serializationObject);
        }
        else if (serializationObject instanceof Map) {
            return serializeMap((Map) serializationObject);
        }

        return serialize(getObjectFieldNamesAndValues(serializationObject));
    }

    protected String serializeMap(Map serializationObject) {
        StringBuilder builder = new StringBuilder("{");

        for(Object objectEntry : serializationObject.entrySet().toArray()) {
            Map.Entry entry = (Map.Entry) objectEntry;

            builder.append("\"").append(entry.getKey()).append("\"");
            builder.append(":");
            builder.append(serialize(entry.getValue()));
            builder.append(",");
        }

        if (",".equals(String.valueOf(builder.charAt(builder.length() - 1))))
            builder.deleteCharAt(builder.length() - 1);

        return builder.append("}").toString();
    }

    protected String serializeCollection(Collection serializationObject) {
        StringBuilder builder = new StringBuilder("[");

        for (Object subObject : serializationObject){
            builder.append(serialize(subObject)).append(",");
        }

        return builder.deleteCharAt(builder.length()-1).append("]").toString();
    }

    protected String serializeNumber(Number serializationObject) {
        return String.valueOf(serializationObject);
    }

    protected String serializeString(String serializationObject) {
        return "\"" + serializationObject + "\"";
    }

    protected String serializeCharacter(Character serializationObject) {
        return "'" + serializationObject + "'";
    }

    protected String serializeNull() {
        return "\"\"";
    }

    protected  <T> Map<String, Object> getObjectFieldNamesAndValues(T object) {
        Map<String, Object> fieldNamesAndValues = new HashMap<>();

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            String fieldName = field.getName();

            try {
                fieldNamesAndValues.put(fieldName, field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return fieldNamesAndValues;
    }
}
