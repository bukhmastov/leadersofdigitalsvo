package ru.leadersofdigitalsvo.app.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leadersofdigitalsvo.app.dao.chain.account.AccountUpdateValueUseCase;
import ru.leadersofdigitalsvo.app.dao.chain.billing.*;
import ru.leadersofdigitalsvo.app.dao.endpoint.BankProcessingEndpoint;
import ru.leadersofdigitalsvo.app.model.ChainIdentity;
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
        String userName = "account1@leadersofdigitalsvo.ru";
        ChainIdentity chainIdentity = makeChainIdentity();
        new AccomplishBillUseCase().run(chainIdentity, userName, billId);
    }

    @Override
    public void fail(String billId) throws IOException {
        String userName = "account1@leadersofdigitalsvo.ru";
        ChainIdentity chainIdentity = makeChainIdentity();
        new FailBillUseCase().run(chainIdentity, userName, billId);
    }

    private void subscribeForBillingIssue() throws IOException {
        ChainIdentity chainIdentity = makeChainIdentity();
        new SubscribeForBillIssue().subscribe(chainIdentity, userName, billId -> {
            try {
                BillState billState = new GetBillUseCase().run(chainIdentity, userName, billId);
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
        ChainIdentity chainIdentity = makeChainIdentity();
        new SubscribeForBillAccomplish().subscribe(chainIdentity, userName, billId -> {
            try {
                BillState billState = new GetBillUseCase().run(chainIdentity, userName, billId);
                int amount = billState.getAmount();
                new AccountUpdateValueUseCase().run(chainIdentity, userName, billId, amount);
            } catch (IOException e) {
                // Nothing for now
                e.printStackTrace();
            }
        });
    }

    @Autowired
    BankProcessingEndpoint bankProcessingEndpoint;

    private String userName = "service@leadersofdigitalsvo.ru";
    private ChainIdentity makeChainIdentity() {
        return new ChainIdentity("network", "chaincode");
    }
}
