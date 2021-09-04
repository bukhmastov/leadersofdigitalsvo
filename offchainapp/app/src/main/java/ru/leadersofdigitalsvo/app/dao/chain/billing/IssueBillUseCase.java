package ru.leadersofdigitalsvo.app.dao.chain.billing;

import org.hyperledger.fabric.gateway.Contract;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.BillState;

import java.io.IOException;

import static ru.leadersofdigitalsvo.app.domain.support.ChainNetworkSupport.useNetwork;
import static ru.leadersofdigitalsvo.app.domain.util.EntityId.makeEntityId;

public class IssueBillUseCase {

    public BillState run(String networkName, String userName, String accountId, String agreementId, int amount) throws IOException {
        return useNetwork(networkName, userName, network -> {
            Contract contract = network.getContract(ChainRegister.billingChaincode, ChainRegister.billingContract);
            String billId = makeEntityId();
            byte[] response = contract.createTransaction("issue")
                    .submit(
                            billId,
                            accountId,
                            agreementId,
                            String.valueOf(amount)
                    );
            return BillState.deserialize(response);
        });
    }
}
