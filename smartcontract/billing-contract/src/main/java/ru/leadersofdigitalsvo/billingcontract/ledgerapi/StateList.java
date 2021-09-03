package ru.leadersofdigitalsvo.billingcontract.ledgerapi;

import org.hyperledger.fabric.contract.Context;
import ru.leadersofdigitalsvo.billingcontract.ledgerapi.impl.StateListImpl;

public interface StateList {

    static StateList getStateList(Context ctx, String listName, StateDeserializer deserializer) {
        return new StateListImpl(ctx, listName, deserializer);
    }

    StateList add(State state);

    StateList update(State state);

    State get(String key);
}
