package ru.leadersofdigitalsvo.accountcontract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.AccountState;
import ru.leadersofdigitalsvo.common.model.State;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Default
@Contract(name = ChainRegister.accountContract)
public class AccountContract implements ContractInterface {

    @Override
    public Context createContext(ChaincodeStub stub) {
        return new AccountContext(stub);
    }

    @Transaction
    public void instantiate(AccountContext ctx) {
        LOG.info("No data migration");
    }

    @Transaction
    public AccountState register(AccountContext ctx, String accountId, int value, int lastValueChangeTime) {
        String mspId = ctx.getClientIdentity().getMSPID();
        String state = AccountState.STATE_ACTIVE;
        AccountState accountState = AccountState.createInstance(accountId, mspId, state, value, lastValueChangeTime);
        ctx.accountList.add(accountState);
        ctx.getStub().setEvent("account-register", accountId.getBytes(StandardCharsets.UTF_8));
        return accountState;
    }

    @Transaction
    public AccountState get(AccountContext ctx, String accountId) {
        String key = State.makeKey(new String[]{accountId});
        AccountState accountState = ctx.accountList.get(key);
        return accountState;
    }

    @Transaction
    public AccountState updateAgreementRef(AccountContext ctx, String accountId, String agreementId) {
        String key = State.makeKey(new String[]{accountId});
        AccountState accountState = ctx.accountList.get(key);
        if (accountState.isClosed()) {
            throw new RuntimeException("Account " + accountState.getAccountId() + " is closed");
        }
        accountState.setAgreementId(agreementId);
        ctx.accountList.update(accountState);
        ctx.getStub().setEvent("account-update", accountId.getBytes(StandardCharsets.UTF_8));
        return accountState;
    }

    @Transaction
    public AccountState updateValue(AccountContext ctx, String accountId, int valueDelta) {
        String key = State.makeKey(new String[]{accountId});
        AccountState accountState = ctx.accountList.get(key);
        if (accountState.isClosed()) {
            throw new RuntimeException("Account " + accountState.getAccountId() + " is closed");
        }
        int valueNext = accountState.getValue() + valueDelta;
        accountState.setValue(valueNext);
        ctx.accountList.update(accountState);
        ctx.getStub().setEvent("account-update", accountId.getBytes(StandardCharsets.UTF_8));
        return accountState;
    }

    private final static Logger LOG = Logger.getLogger(AccountContract.class.getName());
}
