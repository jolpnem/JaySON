package ru.test.JaySON.Serializer;

public class CompositeSerializer implements Serializer {
    private Serializer[] serializers;

    public CompositeSerializer(Serializer[] serializers) {
        for (Serializer serializer : serializers) {
            if (serializer instanceof SerializerAware)
                ((SerializerAware) serializer).setSerializer(this);
        }

        this.serializers = serializers;
    }

    @Override
    public String serialize(Object serializationObject) {
        for (Serializer serializer : serializers) {
            if (serializer.supports(serializationObject))
                return serializer.serialize(serializationObject);
        }

        return "";
    }

    @Override
    public boolean supports(Object object) {
        return true;
    }
}
