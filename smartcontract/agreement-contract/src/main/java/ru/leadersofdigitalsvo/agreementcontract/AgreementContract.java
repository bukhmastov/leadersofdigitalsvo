package ru.leadersofdigitalsvo.agreementcontract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.logging.Logger;

@Default
@Contract(name = "ru.leadersofdigitalsvo.agreementcontract")
public class AgreementContract implements ContractInterface {

    @Override
    public Context createContext(ChaincodeStub stub) {
        return new AgreementContext(stub);
    }

    @Transaction
    public void instantiate(AgreementContext ctx) {
        LOG.info("No data migration");
    }

    @Transaction
    public Agreement registerAgreement(AgreementContext ctx, String agreementId, String accountId, int period, int periodAmount, int periodPercent, int safeAmount) {
        Agreement agreement = Agreement.createInstance(agreementId, accountId, period, periodAmount, periodPercent, safeAmount);
        ctx.agreementList.add(agreement);
        return agreement;
    }


    private final static Logger LOG = Logger.getLogger(AgreementContract.class.getName());
}
