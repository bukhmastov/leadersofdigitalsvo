package ru.leadersofdigitalsvo.agreementcontract.ledgerapi;

@FunctionalInterface
public interface StateDeserializer {
    State deserialize(byte[] buffer);
}
