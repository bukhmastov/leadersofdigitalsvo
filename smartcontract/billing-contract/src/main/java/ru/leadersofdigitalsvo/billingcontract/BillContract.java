package ru.leadersofdigitalsvo.billingcontract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.logging.Logger;

@Default
@Contract(name = "ru.leadersofdigitalsvo.billingcontract")
public class BillContract implements ContractInterface {

    @Override
    public Context createContext(ChaincodeStub stub) {
        return new BillContext(stub);
    }

    @Transaction
    public void instantiate(BillContext ctx) {
        LOG.info("No data migration");
    }

    @Transaction
    public Bill registerAgreement(BillContext ctx, String billId, String accountId, String agreementId) {
        Bill bill = Bill.createInstance(billId, accountId, agreementId);
        ctx.billList.add(bill);
        return bill;
    }

    private final static Logger LOG = Logger.getLogger(BillContract.class.getName());
}
