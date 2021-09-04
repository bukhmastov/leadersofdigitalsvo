package ru.leadersofdigitalsvo.app.domain.service;

import org.springframework.stereotype.Service;
import ru.leadersofdigitalsvo.app.dao.chain.agreement.GetAgreementUseCase;
import ru.leadersofdigitalsvo.app.dao.chain.billing.IssueBillUseCase;
import ru.leadersofdigitalsvo.app.dao.chain.agreement.SubscribeForAgreementRegistration;
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
        String networkName = "network";
        new SubscribeForAgreementRegistration().subscribe(networkName, userName, agreementId -> {
            try {
                AgreementState agreementState = new GetAgreementUseCase().run(networkName, userName, agreementId);
                String accountId = agreementState.getAccountId();
                int amount = agreementState.getPeriodAmount();
                new IssueBillUseCase().run(networkName, userName, accountId, agreementId, amount);
            } catch (IOException e) {
                // Nothing for now
                e.printStackTrace();
            }
        });
    }

    private String userName = "org1@leadersofdigitalsvo.ru";
}
