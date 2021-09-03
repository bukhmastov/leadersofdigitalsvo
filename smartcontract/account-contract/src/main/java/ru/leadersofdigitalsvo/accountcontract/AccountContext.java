package ru.leadersofdigitalsvo.accountcontract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class AccountContext extends Context {

    public AccountContext(ChaincodeStub stub) {
        super(stub);
        this.accountList = new AccountList(this);
    }

    public AccountList accountList;
}
