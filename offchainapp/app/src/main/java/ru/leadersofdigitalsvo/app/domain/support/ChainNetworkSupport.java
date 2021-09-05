package ru.leadersofdigitalsvo.app.domain.support;

import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import ru.leadersofdigitalsvo.app.dao.chain.support.NetworkSupport;
import ru.leadersofdigitalsvo.app.dao.chain.support.UserIdentitySupport;
import ru.leadersofdigitalsvo.app.model.UserChainIdentity;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

public class ChainNetworkSupport {

    public static <T> T useNetwork(String networkName, String userName, Runner<T> runner) throws IOException {
        String org = userName.split("@")[1].split("\\.")[0];
        UserChainIdentity userChainIdentity = UserIdentitySupport.makeUserIdentity(userName);
        Path networkConfig = NetworkSupport.makeNetworkConfig(org);
        try (Gateway gateway = Gateway.createBuilder()
                .identity(userChainIdentity.getWallet(), userChainIdentity.getUserName())
                .networkConfig(networkConfig)
                .connect()
        ) {
            Network network = gateway.getNetwork(networkName);
            return runner.run(network);
        } catch (ContractException | InterruptedException | TimeoutException e) {
            throw new IOException(e);
        }
    }

    @FunctionalInterface
    public interface Runner<T> {
        T run(Network network) throws IOException, ContractException, InterruptedException, TimeoutException;
    }
}
