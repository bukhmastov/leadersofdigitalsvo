package ru.leadersofdigitalsvo.app.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leadersofdigitalsvo.app.dao.chain.account.AccountUpdateValueUseCase;
import ru.leadersofdigitalsvo.app.dao.chain.billing.*;
import ru.leadersofdigitalsvo.app.dao.endpoint.BankProcessingEndpoint;
import ru.leadersofdigitalsvo.common.model.BillState;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class BillingProcessingServiceImpl implements BillingProcessingService {

    @PostConstruct
    public void init() throws IOException {
        subscribeForBillingIssue();
        subscribeForBillingAccomplish();
    }

    @Override
    public void accomplish(String billId) throws IOException {
        String userName = "org1user1@org1.leadersofdigitalsvo.ru";
        String networkName = "network";
        new AccomplishBillUseCase().run(networkName, userName, billId);
    }

    @Override
    public void fail(String billId) throws IOException {
        String userName = "org1user1@org1.leadersofdigitalsvo.ru";
        String networkName = "network";
        new FailBillUseCase().run(networkName, userName, billId);
    }

    private void subscribeForBillingIssue() throws IOException {
        String networkName = "network";
        new SubscribeForBillIssue().subscribe(networkName, userName, billId -> {
            try {
                BillState billState = new GetBillUseCase().run(networkName, userName, billId);
                String accountId = billState.getAccountId();
                int amount = billState.getAmount();
                bankProcessingEndpoint.withdraw(accountId, billId, amount);
            } catch (IOException e) {
                // Nothing for now
                e.printStackTrace();
            }
        });
    }

    private void subscribeForBillingAccomplish() throws IOException {
        String networkName = "network";
        new SubscribeForBillAccomplish().subscribe(networkName, userName, billId -> {
            try {
                BillState billState = new GetBillUseCase().run(networkName, userName, billId);
                int amount = billState.getAmount();
                new AccountUpdateValueUseCase().run(networkName, userName, billId, amount);
            } catch (IOException e) {
                // Nothing for now
                e.printStackTrace();
            }
        });
    }

    @Autowired
    BankProcessingEndpoint bankProcessingEndpoint;

    private String userName = "org1admin@org1.leadersofdigitalsvo.ru";
}
