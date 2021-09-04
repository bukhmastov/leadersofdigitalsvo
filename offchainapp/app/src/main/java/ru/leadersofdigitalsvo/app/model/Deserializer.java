package ru.leadersofdigitalsvo.app.model;

@FunctionalInterface
public interface Deserializer<T> {
    T deserialize(byte[] buffer);
}
