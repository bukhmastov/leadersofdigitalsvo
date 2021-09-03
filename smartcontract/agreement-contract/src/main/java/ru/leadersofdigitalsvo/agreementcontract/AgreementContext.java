package ru.leadersofdigitalsvo.agreementcontract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class AgreementContext extends Context {

    public AgreementContext(ChaincodeStub stub) {
        super(stub);
        this.agreementList = new AgreementList(this);
    }

    public AgreementList agreementList;
}
