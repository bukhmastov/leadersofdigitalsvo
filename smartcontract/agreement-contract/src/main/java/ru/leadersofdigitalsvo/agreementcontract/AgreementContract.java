package ru.leadersofdigitalsvo.agreementcontract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;
import ru.leadersofdigitalsvo.common.ChainRegister;
import ru.leadersofdigitalsvo.common.model.AgreementState;
import ru.leadersofdigitalsvo.common.model.State;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Default
@Contract(name = ChainRegister.agreementContract)
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
    public AgreementState register(AgreementContext ctx, String agreementId, String accountId, int period, int periodAmount, int periodPercent, int safeAmount) {
        AgreementState agreementState = AgreementState.createInstance(agreementId, accountId, period, periodAmount, periodPercent, safeAmount);
        ctx.agreementList.add(agreementState);
        ctx.getStub().setEvent("agreement-register", agreementId.getBytes(StandardCharsets.UTF_8));
        return agreementState;
    }

    @Transaction
    public AgreementState get(AgreementContext ctx, String agreementId) {
        String key = State.makeKey(new String[]{agreementId});
        AgreementState agreementState = ctx.agreementList.get(key);
        return agreementState;
    }


    private final static Logger LOG = Logger.getLogger(AgreementContract.class.getName());
}
