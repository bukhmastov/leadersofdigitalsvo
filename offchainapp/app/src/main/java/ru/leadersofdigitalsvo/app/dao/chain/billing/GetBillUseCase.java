package ru.leadersofdigitalsvo.app.dao.chain.billing;

import org.hyperledger.fabric.gateway.Contract;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.BillState;

import java.io.IOException;

import static ru.leadersofdigitalsvo.app.domain.support.ChainNetworkSupport.useNetwork;

public class GetBillUseCase {

    public BillState run(String networkName, String userName, String billId) throws IOException {
        return useNetwork(networkName, userName, network -> {
            Contract accountContract = network.getContract(ChainRegister.billingChaincode, ChainRegister.billingContract);
            byte[] response = accountContract.createTransaction("get")
                    .evaluate(billId);
            return BillState.deserialize(response);
        });
    }
}
