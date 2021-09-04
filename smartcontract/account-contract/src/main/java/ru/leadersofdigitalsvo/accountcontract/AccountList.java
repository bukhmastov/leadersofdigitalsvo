package ru.leadersofdigitalsvo.accountcontract;

import org.hyperledger.fabric.contract.Context;
import ru.leadersofdigitalsvo.common.chain.ledgerapi.StateList;
import ru.leadersofdigitalsvo.common.model.AccountState;

public class AccountList {

    private final StateList stateList;

    public AccountList(Context ctx) {
        this.stateList = StateList.getStateList(ctx, AccountList.class.getSimpleName(), AccountState::deserialize);
    }

    public AccountList add(AccountState accountState) {
        stateList.add(accountState);
        return this;
    }

    public AccountState get(String key) {
        return (AccountState) this.stateList.get(key);
    }

    public AccountList update(AccountState accountState) {
        this.stateList.update(accountState);
        return this;
    }
}
