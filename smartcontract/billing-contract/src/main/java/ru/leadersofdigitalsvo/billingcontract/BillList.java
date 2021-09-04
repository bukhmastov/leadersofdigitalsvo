package ru.leadersofdigitalsvo.billingcontract;

import org.hyperledger.fabric.contract.Context;
import ru.leadersofdigitalsvo.common.chain.ledgerapi.StateList;
import ru.leadersofdigitalsvo.common.model.BillState;
import ru.leadersofdigitalsvo.common.model.State;

import java.util.Iterator;

public class BillList {

    private final StateList stateList;

    public BillList(Context ctx) {
        this.stateList = StateList.getStateList(ctx, BillList.class.getSimpleName(), BillState::deserialize);
    }

    public BillList add(BillState billState) {
        stateList.add(billState);
        return this;
    }

    public BillState get(String key) {
        return (BillState) this.stateList.get(key);
    }

    public BillList update(BillState billState) {
        this.stateList.update(billState);
        return this;
    }

    public Iterable<BillState> find(String query) {
        Iterator<State> iterator = this.stateList.find(query).iterator();
        return () -> new Iterator<BillState>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }
            @Override
            public BillState next() {
                return (BillState) iterator.next();
            }
        };
    }
}
