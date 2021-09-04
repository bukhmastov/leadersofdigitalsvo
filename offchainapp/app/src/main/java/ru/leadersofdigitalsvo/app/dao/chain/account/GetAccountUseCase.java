package ru.leadersofdigitalsvo.app.dao.chain.account;

import org.hyperledger.fabric.gateway.Contract;
import ru.leadersofdigitalsvo.app.model.ChainIdentity;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.AccountState;

import java.io.IOException;

import static ru.leadersofdigitalsvo.app.domain.support.ChainNetworkSupport.useNetwork;

public class GetAccountUseCase {

    public AccountState run(ChainIdentity chainIdentity, String userName, String accountId) throws IOException {
        return useNetwork(chainIdentity, userName, network -> {
            Contract accountContract = network.getContract(chainIdentity.getChaincodeID(), ChainRegister.account);
            byte[] response = accountContract.createTransaction("get")
                    .evaluate(accountId);
            return AccountState.deserialize(response);
        });
    }
}
