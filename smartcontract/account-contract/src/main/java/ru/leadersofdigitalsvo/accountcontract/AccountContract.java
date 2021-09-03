package ru.leadersofdigitalsvo.accountcontract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.logging.Logger;

@Default
@Contract(name = "ru.leadersofdigitalsvo.accountcontract")
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
    public Account registerAccount(AccountContext ctx, String accountId, int value, int lastValueChangeTime) {
        String mspId = ctx.getClientIdentity().getMSPID();
        String state = Account.STATE_ACTIVE;
        Account account = Account.createInstance(accountId, mspId, state, value, lastValueChangeTime);
        ctx.accountList.add(account);
        return account;
    }


    private final static Logger LOG = Logger.getLogger(AccountContract.class.getName());
}
