package ru.test.JaySON;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import ru.test.Address;
import ru.test.JaySON.Serializer.*;
import ru.test.Person;
import ru.test.Phone;

import java.util.ArrayList;
import java.util.List;

class JaySONSerializationTest {
    private JaySON jaySON;
    private Person person;

    @BeforeEach
    void setUp() {
        CompositeSerializer serializer = new CompositeSerializer(new Serializer[]{
                new NullSerializer(),
                new CharSerializer(),
                new NumberSerializer(),
                new StringSerializer(),
                new CollectionSerializer(),
                new MapSerializer(),
                new ObjectSerializer()
        });

        jaySON = new JaySON(serializer);
        person = new Person();
    }

    @Test
    void testSerializeNull() {
        String json = jaySON.serialize(null);

        assertEquals("null", json);
    }

    @Test
    void testSerializeInt() {
        String json = jaySON.serialize(5);

        assertEquals("5", json);
    }

    @Test
    void testSerializeChar() {
        String json = jaySON.serialize('A');

        assertEquals("'A'", json);
    }

    @Test
    void testSerializeObjectWithInts() {
        person.age = 16;
        person.classNumber = 10;
        String json = jaySON.serialize(person);

        assertTrue(json.contains("\"classNumber\":10"));
        assertTrue(json.contains("\"age\":16"));
    }

    @Test
    void testSerializeObjectWithChars() {
        person.gender = 'M';
        String json = jaySON.serialize(person);

        assertTrue(json.contains("\"gender\":'M'"));
    }

    @Test
    void testSerializeObjectWithString() {
        person.name = "Amanat";
        String json = jaySON.serialize(person);

        assertTrue(json.contains("\"name\":\"Amanat\""));
    }

    @Test
    void testSerializeObjectWithObject() {
        Address address = new Address();
        address.street = "Эльтавная 3-я";
        address.houseNumber = 14;

        person.address = address;
        String json = jaySON.serialize(person);

        assertTrue(json.contains("\"address\":{"));
        assertTrue(json.contains("\"street\":\"Эльтавная 3-я\""));
        assertTrue(json.contains("\"houseNumber\":14"));
    }

    @Test
    void testSerializeObjectWithObjectList() {
        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone(89387972195L));
        phones.add(new Phone(88008008080L));

        person.phones = phones;
        String json = jaySON.serialize(person);

        assertTrue(json.contains("\"phones\":["));
        assertTrue(json.contains("89387972195"));
        assertTrue(json.contains("88008008080"));
    }

    @Test

    void testSerializeFullObject() {
        person.age = 16;
        person.classNumber = 10;
        person.gender = 'M';
        person.name = "Amanat";
        person.address = new Address("Эльтавная 3-я", 14);
        person.phones = List.of(new Phone(89387972195L), new Phone(88008008080L));

        String json = jaySON.serialize(person);

        assertTrue(json.contains("\"classNumber\":10"));
        assertTrue(json.contains("\"age\":16"));
        assertTrue(json.contains("\"gender\":'M'"));
        assertTrue(json.contains("\"name\":\"Amanat\""));
        assertTrue(json.contains("\"address\":{"));
        assertTrue(json.contains("\"street\":\"Эльтавная 3-я\""));
        assertTrue(json.contains("\"houseNumber\":14"));
        assertTrue(json.contains("\"phones\":[{"));
        assertTrue(json.contains("}],"));
        assertTrue(json.contains("89387972195"));
        assertTrue(json.contains("88008008080"));
    }
}
