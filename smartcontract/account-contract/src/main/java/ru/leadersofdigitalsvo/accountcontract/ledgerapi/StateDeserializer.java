package ru.leadersofdigitalsvo.accountcontract.ledgerapi;

@FunctionalInterface
public interface StateDeserializer {
    State deserialize(byte[] buffer);
}
