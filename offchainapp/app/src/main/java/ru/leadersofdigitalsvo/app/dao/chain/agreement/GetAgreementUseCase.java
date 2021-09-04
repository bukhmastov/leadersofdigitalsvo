package ru.leadersofdigitalsvo.app.dao.chain.agreement;

import org.hyperledger.fabric.gateway.Contract;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.AgreementState;

import java.io.IOException;

import static ru.leadersofdigitalsvo.app.domain.support.ChainNetworkSupport.useNetwork;

public class GetAgreementUseCase {

    public AgreementState run(String networkName, String userName, String agreementId) throws IOException {
        return useNetwork(networkName, userName, network -> {
            Contract agreementContract = network.getContract(ChainRegister.agreementChaincode, ChainRegister.agreementContract);
            byte[] response = agreementContract.createTransaction("get")
                    .evaluate(agreementId);
            return AgreementState.deserialize(response);
        });
    }
}
