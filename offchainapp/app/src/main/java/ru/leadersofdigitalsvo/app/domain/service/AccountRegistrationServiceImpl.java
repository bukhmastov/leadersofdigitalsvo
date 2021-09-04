package ru.leadersofdigitalsvo.app.domain.service;

import org.springframework.stereotype.Service;
import ru.leadersofdigitalsvo.app.dao.chain.RegisterAccount;
import ru.leadersofdigitalsvo.app.dao.chain.RegisterAgreement;
import ru.leadersofdigitalsvo.app.model.ChainIdentity;
import ru.leadersofdigitalsvo.app.model.entity.Account;
import ru.leadersofdigitalsvo.app.model.entity.Agreement;

import java.io.IOException;

import static ru.leadersofdigitalsvo.app.domain.util.EntityId.makeEntityId;

@Service
public class AccountRegistrationServiceImpl implements AccountRegistrationService {

    @Override
    public String register(Account account, Agreement agreement) throws IOException {
        String userName = "account1@leadersofdigitalsvo.ru";
        ChainIdentity chainIdentity = makeChainIdentity();
        account.setAccountId(makeEntityId());
        agreement.setAccountId(account.getAccountId());
        agreement.setAgreementId(makeEntityId());
        account = new RegisterAccount().run(chainIdentity, userName, account);
        agreement = new RegisterAgreement().run(chainIdentity, userName, agreement);
        return account.getAccountId();
    }

    private ChainIdentity makeChainIdentity() {
        return new ChainIdentity("network", "chaincode");
    }
}
