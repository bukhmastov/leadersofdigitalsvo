package ru.leadersofdigitalsvo.app.dao.chain.billing;

import org.hyperledger.fabric.gateway.Contract;
import ru.leadersofdigitalsvo.app.model.ChainIdentity;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.BillState;

import java.io.IOException;

import static ru.leadersofdigitalsvo.app.domain.support.ChainNetworkSupport.useNetwork;

public class AccomplishBillUseCase {

    public BillState run(ChainIdentity chainIdentity, String userName, String billId) throws IOException {
        return useNetwork(chainIdentity, userName, network -> {
            Contract contract = network.getContract(chainIdentity.getChaincodeID(), ChainRegister.billing);
            byte[] response = contract.createTransaction("accomplish")
                    .submit(billId);
            return BillState.deserialize(response);
        });
    }
}
