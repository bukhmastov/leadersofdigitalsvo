package ru.leadersofdigitalsvo.agreementcontract;

import org.hyperledger.fabric.contract.Context;
import ru.leadersofdigitalsvo.agreementcontract.ledgerapi.StateList;

public class AgreementList {

    private final StateList stateList;

    public AgreementList(Context ctx) {
        this.stateList = StateList.getStateList(ctx, AgreementList.class.getSimpleName(), Agreement::deserialize);
    }

    public AgreementList add(Agreement agreement) {
        stateList.add(agreement);
        return this;
    }

    public Agreement get(String key) {
        return (Agreement) this.stateList.get(key);
    }

    public AgreementList update(Agreement agreement) {
        this.stateList.update(agreement);
        return this;
    }
}
