package ru.leadersofdigitalsvo.billingcontract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;
import ru.leadersofdigitalsvo.billingcontract.ledgerapi.State;

import java.nio.charset.StandardCharsets;
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
    public Bill issue(BillContext ctx, String billId, String accountId, String agreementId, int amount) {
        String state = Bill.STATE_ISSUED;
        Bill bill = Bill.createInstance(billId, accountId, agreementId, amount, state);
        ctx.billList.add(bill);
        ctx.getStub().setEvent("bill-issue", billId.getBytes(StandardCharsets.UTF_8));
        return bill;
    }

    @Transaction
    public Bill accomplish(BillContext ctx, String billId) {
        String key = State.makeKey(new String[]{billId});
        Bill bill = ctx.billList.get(key);
        if (!bill.isIssued()) {
            throw new RuntimeException("Bill " + bill.getBillId() + " is not issued. Current state is " + bill.getState());
        }
        bill.setState(Bill.STATE_ACCOMPLISHED);
        ctx.billList.update(bill);
        ctx.getStub().setEvent("bill-accomplish", billId.getBytes(StandardCharsets.UTF_8));
        return bill;
    }

    @Transaction
    public Bill fail(BillContext ctx, String billId) {
        String key = State.makeKey(new String[]{billId});
        Bill bill = ctx.billList.get(key);
        if (!bill.isIssued()) {
            throw new RuntimeException("Bill " + bill.getBillId() + " is not issued. Current state is " + bill.getState());
        }
        bill.setState(Bill.STATE_FAILED);
        ctx.billList.update(bill);
        ctx.getStub().setEvent("bill-fail", billId.getBytes(StandardCharsets.UTF_8));
        return bill;
    }

    private final static Logger LOG = Logger.getLogger(BillContract.class.getName());
}
