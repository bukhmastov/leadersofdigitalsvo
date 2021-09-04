package ru.leadersofdigitalsvo.billingcontract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.BillState;
import ru.leadersofdigitalsvo.common.model.State;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Default
@Contract(name = ChainRegister.billingContract)
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
    public BillState issue(BillContext ctx, String billId, String accountId, String agreementId, int amount) {
        String state = BillState.STATE_ISSUED;
        long date = System.currentTimeMillis();
        BillState billState = BillState.createInstance(billId, accountId, agreementId, amount, state, date);
        ctx.billList.add(billState);
        ctx.getStub().setEvent("bill-issue", billId.getBytes(StandardCharsets.UTF_8));
        return billState;
    }

    @Transaction
    public BillState accomplish(BillContext ctx, String billId) {
        String key = State.makeKey(new String[]{billId});
        BillState billState = ctx.billList.get(key);
        if (!billState.isIssued()) {
            throw new RuntimeException("Bill " + billState.getBillId() + " is not issued. Current state is " + billState.getState());
        }
        billState.setState(BillState.STATE_ACCOMPLISHED);
        ctx.billList.update(billState);
        ctx.getStub().setEvent("bill-accomplish", billId.getBytes(StandardCharsets.UTF_8));
        return billState;
    }

    @Transaction
    public BillState fail(BillContext ctx, String billId) {
        String key = State.makeKey(new String[]{billId});
        BillState billState = ctx.billList.get(key);
        if (!billState.isIssued()) {
            throw new RuntimeException("Bill " + billState.getBillId() + " is not issued. Current state is " + billState.getState());
        }
        billState.setState(BillState.STATE_FAILED);
        ctx.billList.update(billState);
        ctx.getStub().setEvent("bill-fail", billId.getBytes(StandardCharsets.UTF_8));
        return billState;
    }

    @Transaction
    public BillState get(BillContext ctx, String billId) {
        String key = State.makeKey(new String[]{billId});
        return ctx.billList.get(key);
    }

    @Transaction
    public List<BillState> getAllForAccount(BillContext ctx, String accountId) {
        return StreamSupport.stream(ctx.billList.find("").spliterator(), false)
                .filter(billState -> accountId.equals(billState.getAccountId()))
                .collect(Collectors.toList());
    }

    private final static Logger LOG = Logger.getLogger(BillContract.class.getName());
}
