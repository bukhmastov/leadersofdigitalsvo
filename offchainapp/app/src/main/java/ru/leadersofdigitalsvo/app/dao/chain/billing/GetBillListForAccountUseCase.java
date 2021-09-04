package ru.leadersofdigitalsvo.app.dao.chain.billing;

import org.hyperledger.fabric.gateway.Contract;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.BillState;

import java.io.IOException;
import java.util.List;

import static ru.leadersofdigitalsvo.app.dao.chain.support.SerializeSupport.deserializeList;
import static ru.leadersofdigitalsvo.app.domain.support.ChainNetworkSupport.useNetwork;

public class GetBillListForAccountUseCase {

    public List<BillState> run(String networkName, String userName, String accountId) throws IOException {
        return useNetwork(networkName, userName, network -> {
            Contract billingContract = network.getContract(ChainRegister.billingChaincode, ChainRegister.billingContract);
            byte[] response = billingContract.createTransaction("getAllForAccount")
                    .evaluate(accountId);
            return deserializeList(response, BillState::deserialize);
        });
    }
}
