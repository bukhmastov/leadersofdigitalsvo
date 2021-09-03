package ru.leadersofdigitalsvo.accountcontract;

import org.hyperledger.fabric.contract.Context;
import ru.leadersofdigitalsvo.accountcontract.ledgerapi.StateList;

public class AccountList {

    private final StateList stateList;

    public AccountList(Context ctx) {
        this.stateList = StateList.getStateList(ctx, AccountList.class.getSimpleName(), Account::deserialize);
    }

    public AccountList add(Account account) {
        stateList.add(account);
        return this;
    }

    public Account get(String key) {
        return (Account) this.stateList.get(key);
    }

    public AccountList update(Account account) {
        this.stateList.update(account);
        return this;
    }
}
