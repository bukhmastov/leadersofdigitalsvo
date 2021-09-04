package ru.leadersofdigitalsvo.common.chain.ledgerapi;

import ru.leadersofdigitalsvo.common.model.State;

@FunctionalInterface
public interface StateDeserializer {
    State deserialize(byte[] buffer);
}
