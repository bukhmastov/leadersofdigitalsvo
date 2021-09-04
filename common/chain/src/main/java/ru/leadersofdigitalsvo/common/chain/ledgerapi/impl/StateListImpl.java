package ru.leadersofdigitalsvo.common.chain.ledgerapi.impl;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import ru.leadersofdigitalsvo.common.chain.ledgerapi.StateDeserializer;
import ru.leadersofdigitalsvo.common.chain.ledgerapi.StateList;
import ru.leadersofdigitalsvo.common.model.State;

import java.util.Iterator;

public class StateListImpl implements StateList {

    private final Context ctx;
    private final String name;
    private final StateDeserializer deserializer;

    public StateListImpl(Context ctx, String listName, StateDeserializer deserializer) {
        this.ctx = ctx;
        this.name = listName;
        this.deserializer = deserializer;
    }

    @Override
    public StateList add(State state) {
        ChaincodeStub stub = this.ctx.getStub();
        CompositeKey ledgerKey = stub.createCompositeKey(this.name, state.getSplitKey());
        byte[] data = State.serialize(state);
        stub.putState(ledgerKey.toString(), data);
        return this;
    }

    @Override
    public StateList update(State state) {
        ChaincodeStub stub = this.ctx.getStub();
        CompositeKey ledgerKey = stub.createCompositeKey(this.name, state.getSplitKey());
        byte[] data = State.serialize(state);
        stub.putState(ledgerKey.toString(), data);
        return this;
    }

    @Override
    public State get(String key) {
        ChaincodeStub stub = this.ctx.getStub();
        CompositeKey ledgerKey = stub.createCompositeKey(this.name, State.splitKey(key));
        byte[] data = stub.getState(ledgerKey.toString());
        if (data == null) {
            return null;
        }
        return this.deserializer.deserialize(data);
    }

    @Override
    public Iterable<State> find(String query) {
        ChaincodeStub stub = this.ctx.getStub();
        Iterator<KeyValue> iterator = stub.getQueryResult(query).iterator();
        return () -> new Iterator<State>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }
            @Override
            public State next() {
                return StateListImpl.this.deserializer.deserialize(iterator.next().getValue());
            }
        };
    }
}
