package ru.test.JaySON.Serializer;

import java.util.Collection;

public class CollectionSerializer implements Serializer, SerializerAware {
    private Serializer serializer;

    @Override
    public String serialize(Object serializationObject) {
        StringBuilder builder = new StringBuilder("[");

        for (Object subObject : (Collection) serializationObject){
            builder.append(serializer.serialize(subObject)).append(",");
        }

        return builder.deleteCharAt(builder.length()-1).append("]").toString();
    }

    @Override
    public boolean supports(Object object) {
        return object instanceof Collection;
    }

    @Override
    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }
}
