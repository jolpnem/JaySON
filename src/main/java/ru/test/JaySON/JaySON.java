package ru.test.JaySON;

import ru.test.JaySON.Serializer.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
public class JaySON {
    protected Serializer serializer;

    public JaySON(Serializer serializer) {
        this.serializer = serializer;
    }

    public String serialize(Object serializationObject) {
        return serializer.serialize(serializationObject);
    }

    public Object deserialize(String json) {
        if (json.matches("^$"))
            return null;
        else if (json.matches("^'.'$"))
            return json.charAt(json.indexOf("'") + 1);
        else if (json.matches("^\\d+$")) {
            Matcher numberMatcher = Pattern.compile("\\d+").matcher(json);

            if (numberMatcher.matches())
                return Integer.valueOf(json.substring(numberMatcher.start(), numberMatcher.end()));
        }
        else if (json.matches("^\".*\"$")) {
            Matcher stringMatcher = Pattern.compile("\".*\"").matcher(json);

            if (stringMatcher.matches())
                return json.substring(stringMatcher.start() + 1, stringMatcher.end() - 1);
        }
        else if (json.matches("^\\[.*]$")) {
            Matcher collectionMatcher = Pattern.compile("\\[.*]").matcher(json);

            if (collectionMatcher.matches()) {
                ArrayList<Object> parts = new ArrayList<>();
                String substring = json.substring(collectionMatcher.start() + 1, collectionMatcher.end() - 1);

                for (String part : substring.split(",")) {
                    parts.add(deserialize(part));
                }

                return parts;
            }
        } else if (json.matches("^\\{.*}$")) {
            Matcher mapMatcher = Pattern.compile("^\\{.*}$").matcher(json);

            if (mapMatcher.matches()) {
                HashMap<String, Object> objectParts = new HashMap<>();
                String substring = json.substring(mapMatcher.start() + 1, mapMatcher.end() - 1);

                for (String objectPart : substring.split(",")) {
                    String[] part = objectPart.split(":");
                    String key = (String) deserialize(part[0]);
                    Object value = deserialize(part[1]);

                    objectParts.put(key, value);
                }

                return objectParts;
            }
        }

        throw new IllegalArgumentException("Invalid Json syntax");
    }
}
