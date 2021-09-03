package ru.leadersofdigitalsvo.billingcontract.ledgerapi;

@FunctionalInterface
public interface StateDeserializer {
    State deserialize(byte[] buffer);
}
