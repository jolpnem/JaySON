package ru.test.JaySON.Serializer;

import java.util.Map;

public class MapSerializer implements Serializer, SerializerAware {
    private Serializer serializer;

    @Override
    public String serialize(Object serializationObject) {
        StringBuilder builder = new StringBuilder("{");

        for(Object objectEntry : ((Map) serializationObject).entrySet().toArray()) {
            Map.Entry entry = (Map.Entry) objectEntry;

            builder.append("\"").append(entry.getKey()).append("\"");
            builder.append(":");
            builder.append(serializer.serialize(entry.getValue()));
            builder.append(",");
        }

        if (",".equals(String.valueOf(builder.charAt(builder.length() - 1))))
            builder.deleteCharAt(builder.length() - 1);

        return builder.append("}").toString();
    }

    @Override
    public boolean supports(Object object) {
        return object instanceof Map;
    }

    @Override
    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }
}
