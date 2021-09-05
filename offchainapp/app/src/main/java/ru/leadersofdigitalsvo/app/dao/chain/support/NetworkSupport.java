package ru.leadersofdigitalsvo.app.dao.chain.support;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkSupport {

    public static Path makeNetworkConfig(String org) {
        return Paths.get("..", "..", "network", "organizations", "peerOrganizations", org + ".leadersofdigitalsvo.ru", "connection-" + org + ".yaml");
    }
}
