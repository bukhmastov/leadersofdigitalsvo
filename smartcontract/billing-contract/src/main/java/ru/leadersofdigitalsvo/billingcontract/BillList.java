package ru.leadersofdigitalsvo.billingcontract;

import org.hyperledger.fabric.contract.Context;
import ru.leadersofdigitalsvo.billingcontract.ledgerapi.StateList;

public class BillList {

    private final StateList stateList;

    public BillList(Context ctx) {
        this.stateList = StateList.getStateList(ctx, BillList.class.getSimpleName(), Bill::deserialize);
    }

    public BillList add(Bill bill) {
        stateList.add(bill);
        return this;
    }

    public Bill get(String key) {
        return (Bill) this.stateList.get(key);
    }

    public BillList update(Bill bill) {
        this.stateList.update(bill);
        return this;
    }
}
