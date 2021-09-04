package ru.leadersofdigitalsvo.app.dao.chain.billing;

import org.hyperledger.fabric.gateway.Contract;
import ru.leadersofdigitalsvo.app.model.ChainIdentity;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.BillState;

import java.io.IOException;

import static ru.leadersofdigitalsvo.app.domain.support.ChainNetworkSupport.useNetwork;
import static ru.leadersofdigitalsvo.app.domain.util.EntityId.makeEntityId;

public class IssueBillUseCase {

    public BillState run(ChainIdentity chainIdentity, String userName, String accountId, String agreementId, int amount) throws IOException {
        return useNetwork(chainIdentity, userName, network -> {
            Contract contract = network.getContract(chainIdentity.getChaincodeID(), ChainRegister.billing);
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
