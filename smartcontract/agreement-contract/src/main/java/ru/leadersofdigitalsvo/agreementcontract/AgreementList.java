package ru.leadersofdigitalsvo.agreementcontract;

import org.hyperledger.fabric.contract.Context;
import ru.leadersofdigitalsvo.common.chain.ledgerapi.StateList;
import ru.leadersofdigitalsvo.common.model.AgreementState;

public class AgreementList {

    private final StateList stateList;

    public AgreementList(Context ctx) {
        this.stateList = StateList.getStateList(ctx, AgreementList.class.getSimpleName(), AgreementState::deserialize);
    }

    public AgreementList add(AgreementState agreementState) {
        stateList.add(agreementState);
        return this;
    }

    public AgreementState get(String key) {
        return (AgreementState) this.stateList.get(key);
    }

    public AgreementList update(AgreementState agreementState) {
        this.stateList.update(agreementState);
        return this;
    }
}
