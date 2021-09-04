package ru.leadersofdigitalsvo.app.dao.chain.support;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkSupport {

    private static final Path networkConfigPath = Paths.get(".", "gateway", "connection.yaml");

    public Path makeNetworkConfig() {
        return networkConfigPath;
    }
}
