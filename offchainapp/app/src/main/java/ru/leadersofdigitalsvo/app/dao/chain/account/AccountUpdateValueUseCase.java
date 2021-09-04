package ru.leadersofdigitalsvo.app.dao.chain.account;

import org.hyperledger.fabric.gateway.Contract;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.AccountState;

import java.io.IOException;

import static ru.leadersofdigitalsvo.app.domain.support.ChainNetworkSupport.useNetwork;

public class AccountUpdateValueUseCase {

    public AccountState run(String networkName, String userName, String accountId, int valueDelta) throws IOException {
        return useNetwork(networkName, userName, network -> {
            Contract contract = network.getContract(ChainRegister.accountChaincode, ChainRegister.accountContract);
            byte[] response = contract.createTransaction("updateValue")
                    .submit(accountId, String.valueOf(valueDelta));
            return AccountState.deserialize(response);
        });
    }
}
