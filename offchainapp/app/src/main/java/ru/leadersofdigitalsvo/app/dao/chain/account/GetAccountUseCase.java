package ru.leadersofdigitalsvo.app.dao.chain.account;

import org.hyperledger.fabric.gateway.Contract;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.AccountState;

import java.io.IOException;

import static ru.leadersofdigitalsvo.app.domain.support.ChainNetworkSupport.useNetwork;

public class GetAccountUseCase {

    public AccountState run(String networkName, String userName, String accountId) throws IOException {
        return useNetwork(networkName, userName, network -> {
            Contract accountContract = network.getContract(ChainRegister.accountChaincode, ChainRegister.accountContract);
            byte[] response = accountContract.createTransaction("get")
                    .evaluate(accountId);
            return AccountState.deserialize(response);
        });
    }
}
