package ru.leadersofdigitalsvo.app.dao.chain.support;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.leadersofdigitalsvo.app.model.Deserializer;

import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SerializeSupport {

    public static <T> List<T> deserializeList(byte[] data, Deserializer<T> deserializer) {
        JSONArray json = new JSONArray(new String(data, UTF_8));
        return json.toList()
                .stream()
                .filter(item -> item instanceof JSONObject)
                .map(item -> deserializer.deserialize(item.toString().getBytes(UTF_8)))
                .collect(Collectors.toList());
    }
}

