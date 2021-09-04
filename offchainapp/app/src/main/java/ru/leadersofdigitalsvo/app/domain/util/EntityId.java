package ru.leadersofdigitalsvo.app.domain.util;

import java.util.UUID;

public class EntityId {

    public static String makeEntityId() {
        return UUID.randomUUID().toString();
    }
}
