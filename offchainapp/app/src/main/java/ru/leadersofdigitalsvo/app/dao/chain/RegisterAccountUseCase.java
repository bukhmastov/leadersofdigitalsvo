package ru.leadersofdigitalsvo.app.dao.chain;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.app.dao.chain.support.NetworkSupport;
import ru.leadersofdigitalsvo.app.dao.chain.support.UserIdentitySupport;
import ru.leadersofdigitalsvo.app.model.ChainIdentity;
import ru.leadersofdigitalsvo.app.model.UserChainIdentity;
import ru.leadersofdigitalsvo.app.model.entity.Account;
import ru.leadersofdigitalsvo.app.model.entity.Agreement;
import ru.leadersofdigitalsvo.common.model.AccountState;
import ru.leadersofdigitalsvo.common.model.AgreementState;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

public class RegisterAccountUseCase {

    public void run(ChainIdentity chainIdentity, String userName, Account account, Agreement agreement) throws IOException {
        UserChainIdentity userChainIdentity = new UserIdentitySupport().makeUserIdentity(userName);
        Path networkConfig = new NetworkSupport().makeNetworkConfig();
        try (Gateway gateway = Gateway.createBuilder()
                .identity(userChainIdentity.getWallet(), userChainIdentity.getUserName())
                .networkConfig(networkConfig)
                .connect()
        ) {
            Network network = gateway.getNetwork(chainIdentity.getNetworkName());
            AccountState accountState = registerAccount(chainIdentity, network, account);
            AgreementState agreementState = registerAgreement(chainIdentity, network, agreement);
            accountState.setAgreementId(agreementState.getAgreementId());
            updateAccountAgreementRef(chainIdentity, network, accountState);
        } catch (ContractException | InterruptedException | TimeoutException e) {
            throw new IOException(e);
        }
    }

    private AccountState registerAccount(ChainIdentity chainIdentity, Network network, Account account) throws ContractException, InterruptedException, TimeoutException {
        Contract contract = network.getContract(chainIdentity.getChaincodeID(), ChainRegister.account);
        byte[] response = contract.createTransaction("register")
                .submit(
                        account.getAccountId(),
                        String.valueOf(account.getValue()),
                        String.valueOf(System.currentTimeMillis())
                );
        return AccountState.deserialize(response);
    }

    private AgreementState registerAgreement(ChainIdentity chainIdentity, Network network, Agreement agreement) throws ContractException, InterruptedException, TimeoutException {
        Contract contract = network.getContract(chainIdentity.getChaincodeID(), ChainRegister.agreement);
        byte[] response = contract.createTransaction("register")
                .submit(
                        agreement.getAgreementId(),
                        agreement.getAccountId()
                );
        return AgreementState.deserialize(response);
    }

    private void updateAccountAgreementRef(ChainIdentity chainIdentity, Network network, AccountState account) throws ContractException, InterruptedException, TimeoutException {
        Contract contract = network.getContract(chainIdentity.getChaincodeID(), ChainRegister.account);
        contract.createTransaction("updateAgreementRef")
                .submit(
                        account.getAccountId(),
                        account.getAgreementId()
                );
    }
}
