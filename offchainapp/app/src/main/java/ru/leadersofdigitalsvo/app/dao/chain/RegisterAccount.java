package ru.leadersofdigitalsvo.app.dao.chain;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import ru.leadersofdigitalsvo.app.dao.chain.support.NetworkSupport;
import ru.leadersofdigitalsvo.app.dao.chain.support.UserIdentitySupport;
import ru.leadersofdigitalsvo.app.model.ChainIdentity;
import ru.leadersofdigitalsvo.app.model.UserIdentity;
import ru.leadersofdigitalsvo.app.model.chain.AccountState;
import ru.leadersofdigitalsvo.app.model.entity.Account;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

public class RegisterAccount {

    private static final String contractID = "ru.leadersofdigitalsvo.accountcontract";

    public Account run(ChainIdentity chainIdentity, String userName, Account account) throws IOException {
        UserIdentity userIdentity = new UserIdentitySupport().makeUserIdentity(userName);
        Path networkConfig = new NetworkSupport().makeNetworkConfig();
        try (Gateway gateway = Gateway.createBuilder()
                .identity(userIdentity.getWallet(), userIdentity.getUserName())
                .networkConfig(networkConfig)
                .connect()
        ) {
            Network network = gateway.getNetwork(chainIdentity.getNetworkName());
            Contract contract = network.getContract(chainIdentity.getChaincodeID(), contractID);
            byte[] response = contract.createTransaction("registerAccount")
                    .submit(
                            account.getAccountId(),
                            String.valueOf(account.getValue()),
                            String.valueOf(System.currentTimeMillis())
                    );
            AccountState accountChain = AccountState.deserialize(response);
            account.setState(accountChain.getState());
            account.setValue(accountChain.getValue());
            return account;
        } catch (ContractException | InterruptedException | TimeoutException e) {
            throw new IOException(e);
        }
    }
}
