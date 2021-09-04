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
import ru.leadersofdigitalsvo.app.model.entity.AccountInfo;
import ru.leadersofdigitalsvo.app.model.entity.Agreement;
import ru.leadersofdigitalsvo.app.model.entity.Bill;
import ru.leadersofdigitalsvo.common.model.AccountState;
import ru.leadersofdigitalsvo.common.model.AgreementState;
import ru.leadersofdigitalsvo.common.model.BillState;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static ru.leadersofdigitalsvo.app.dao.chain.support.SerializeSupport.deserializeList;

public class AccountInfoUseCase {

    public AccountInfo run(ChainIdentity chainIdentity, String userName, String accountId) throws IOException {
        UserChainIdentity userChainIdentity = new UserIdentitySupport().makeUserIdentity(userName);
        Path networkConfig = new NetworkSupport().makeNetworkConfig();
        try (Gateway gateway = Gateway.createBuilder()
                .identity(userChainIdentity.getWallet(), userChainIdentity.getUserName())
                .networkConfig(networkConfig)
                .connect()
        ) {
            Network network = gateway.getNetwork(chainIdentity.getNetworkName());
            AccountState accountState = getAccount(chainIdentity, network, accountId);
            Account account = new Account(accountState.getAccountId(), accountState.getState(), accountState.getValue());
            AgreementState agreementState = getAgreement(chainIdentity, network, accountState.getAgreementId());
            Agreement agreement = new Agreement(agreementState.getAgreementId(), agreementState.getAccountId(), agreementState.getPeriod(), agreementState.getPeriodAmount(), agreementState.getPeriodPercent(), agreementState.getSafeAmount());
            List<BillState> billStateList = getBills(chainIdentity, network, accountState.getAccountId());
            List<Bill> billList = billStateList.stream()
                    .map(bill -> new Bill(bill.getBillId(), bill.getAccountId(), bill.getAgreementId(), bill.getAmount(), bill.getState(), bill.getDate()))
                    .collect(Collectors.toList());
            return new AccountInfo(account, agreement, billList);
        } catch (ContractException e) {
            throw new IOException(e);
        }
    }

    private AccountState getAccount(ChainIdentity chainIdentity, Network network, String accountId) throws IOException, ContractException {
        Contract accountContract = network.getContract(chainIdentity.getChaincodeID(), ChainRegister.account);
        byte[] response = accountContract.createTransaction("get")
                .evaluate(accountId);
        return AccountState.deserialize(response);
    }

    private AgreementState getAgreement(ChainIdentity chainIdentity, Network network, String agreementId) throws ContractException {
        Contract agreementContract = network.getContract(chainIdentity.getChaincodeID(), ChainRegister.agreement);
        byte[] response = agreementContract.createTransaction("get")
                .evaluate(agreementId);
        return AgreementState.deserialize(response);
    }

    private List<BillState> getBills(ChainIdentity chainIdentity, Network network, String accountId) throws ContractException {
        Contract billingContract = network.getContract(chainIdentity.getChaincodeID(), ChainRegister.billing);
        byte[] response = billingContract.createTransaction("getAllForAccount")
                .evaluate(accountId);
        return deserializeList(response, BillState::deserialize);
    }
}
