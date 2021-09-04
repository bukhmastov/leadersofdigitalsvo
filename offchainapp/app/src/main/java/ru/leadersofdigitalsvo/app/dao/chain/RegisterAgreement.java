package ru.leadersofdigitalsvo.app.dao.chain;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import ru.leadersofdigitalsvo.app.dao.chain.support.NetworkSupport;
import ru.leadersofdigitalsvo.app.dao.chain.support.UserIdentitySupport;
import ru.leadersofdigitalsvo.app.model.ChainIdentity;
import ru.leadersofdigitalsvo.app.model.UserIdentity;
import ru.leadersofdigitalsvo.app.model.chain.AgreementState;
import ru.leadersofdigitalsvo.app.model.entity.Agreement;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

public class RegisterAgreement {

    private static final String contractID = "ru.leadersofdigitalsvo.agreementcontract";

    public Agreement run(ChainIdentity chainIdentity, String userName, Agreement agreement) throws IOException {
        UserIdentity userIdentity = new UserIdentitySupport().makeUserIdentity(userName);
        Path networkConfig = new NetworkSupport().makeNetworkConfig();
        try (Gateway gateway = Gateway.createBuilder()
                .identity(userIdentity.getWallet(), userIdentity.getUserName())
                .networkConfig(networkConfig)
                .connect()
        ) {
            Network network = gateway.getNetwork(chainIdentity.getNetworkName());
            Contract contract = network.getContract(chainIdentity.getChaincodeID(), contractID);
            byte[] response = contract.createTransaction("registerAgreement")
                    .submit(
                            agreement.getAgreementId(),
                            agreement.getAccountId()
                    );
            AgreementState agreementState = AgreementState.deserialize(response);
            return agreement;
        } catch (ContractException | InterruptedException | TimeoutException e) {
            throw new IOException(e);
        }
    }
}
