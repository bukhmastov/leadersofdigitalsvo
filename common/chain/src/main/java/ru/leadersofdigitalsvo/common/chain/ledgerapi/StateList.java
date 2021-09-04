package ru.leadersofdigitalsvo.common.chain.ledgerapi;

import org.hyperledger.fabric.contract.Context;
import ru.leadersofdigitalsvo.common.chain.ledgerapi.impl.StateListImpl;
import ru.leadersofdigitalsvo.common.model.State;

public interface StateList {

    static StateList getStateList(Context ctx, String listName, StateDeserializer deserializer) {
        return new StateListImpl(ctx, listName, deserializer);
    }

    StateList add(State state);

    StateList update(State state);

    State get(String key);

    Iterable<State> find(String query);
}
