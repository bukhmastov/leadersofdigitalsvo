package ru.leadersofdigitalsvo.app.dao.chain.account;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Network;
import ru.leadersofdigitalsvo.app.model.entity.Account;
import ru.leadersofdigitalsvo.app.model.entity.Agreement;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.AccountState;
import ru.leadersofdigitalsvo.common.model.AgreementState;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static ru.leadersofdigitalsvo.app.domain.support.ChainNetworkSupport.useNetwork;

public class RegisterAccountUseCase {

    public void run(String networkName, String userName, Account account, Agreement agreement) throws IOException {
        useNetwork(networkName, userName, network -> {
            AccountState accountState = registerAccount(network, account);
            AgreementState agreementState = registerAgreement(network, agreement);
            accountState.setAgreementId(agreementState.getAgreementId());
            updateAccountAgreementRef(network, accountState);
            return accountState;
        });
    }

    private AccountState registerAccount(Network network, Account account) throws ContractException, InterruptedException, TimeoutException {
        Contract contract = network.getContract(ChainRegister.accountChaincode, ChainRegister.accountContract);
        byte[] response = contract.createTransaction("register")
                .submit(
                        account.getAccountId(),
                        String.valueOf(account.getValue()),
                        String.valueOf(System.currentTimeMillis())
                );
        return AccountState.deserialize(response);
    }

    private AgreementState registerAgreement(Network network, Agreement agreement) throws ContractException, InterruptedException, TimeoutException {
        Contract contract = network.getContract(ChainRegister.agreementChaincode, ChainRegister.agreementContract);
        byte[] response = contract.createTransaction("register")
                .submit(
                        agreement.getAgreementId(),
                        agreement.getAccountId()
                );
        return AgreementState.deserialize(response);
    }

    private void updateAccountAgreementRef(Network network, AccountState account) throws ContractException, InterruptedException, TimeoutException {
        Contract contract = network.getContract(ChainRegister.accountChaincode, ChainRegister.accountContract);
        contract.createTransaction("updateAgreementRef")
                .submit(
                        account.getAccountId(),
                        account.getAgreementId()
                );
    }
}
