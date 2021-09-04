package ru.leadersofdigitalsvo.app.domain.service;

import org.springframework.stereotype.Service;
import ru.leadersofdigitalsvo.app.dao.chain.agreement.GetAgreementUseCase;
import ru.leadersofdigitalsvo.app.dao.chain.billing.IssueBillUseCase;
import ru.leadersofdigitalsvo.app.dao.chain.agreement.SubscribeForAgreementRegistration;
import ru.leadersofdigitalsvo.app.model.ChainIdentity;
import ru.leadersofdigitalsvo.common.model.AgreementState;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class BillingServiceImpl implements BillingService {

    @PostConstruct
    public void init() throws IOException {
        subscribeForAgreementRegistration();
    }

    private void subscribeForAgreementRegistration() throws IOException {
        ChainIdentity chainIdentity = makeChainIdentity();
        new SubscribeForAgreementRegistration().subscribe(chainIdentity, userName, agreementId -> {
            try {
                AgreementState agreementState = new GetAgreementUseCase().run(chainIdentity, userName, agreementId);
                String accountId = agreementState.getAccountId();
                int amount = agreementState.getPeriodAmount();
                new IssueBillUseCase().run(chainIdentity, userName, accountId, agreementId, amount);
            } catch (IOException e) {
                // Nothing for now
                e.printStackTrace();
            }
        });
    }

    private String userName = "service@leadersofdigitalsvo.ru";
    private ChainIdentity makeChainIdentity() {
        return new ChainIdentity("network", "chaincode");
    }
}
