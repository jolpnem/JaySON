package ru.test;

public class Phone {
    private long number;

    public Phone(long number) {
        if (String.valueOf(number).length() != 11)
            throw new IllegalArgumentException("Invalid phone number");

        this.number = number;
    }
}
