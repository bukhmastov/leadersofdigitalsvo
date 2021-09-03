package ru.leadersofdigitalsvo.billingcontract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class BillContext extends Context {

    public BillContext(ChaincodeStub stub) {
        super(stub);
        this.billList = new BillList(this);
    }

    public BillList billList;
}
