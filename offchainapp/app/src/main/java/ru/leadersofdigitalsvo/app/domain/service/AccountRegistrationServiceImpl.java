package ru.leadersofdigitalsvo.app.domain.service;

import org.springframework.stereotype.Service;
import ru.leadersofdigitalsvo.app.dao.chain.AccountInfoUseCase;
import ru.leadersofdigitalsvo.app.dao.chain.RegisterAccountUseCase;
import ru.leadersofdigitalsvo.app.model.ChainIdentity;
import ru.leadersofdigitalsvo.app.model.entity.Account;
import ru.leadersofdigitalsvo.app.model.entity.AccountInfo;
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
        new RegisterAccountUseCase().run(chainIdentity, userName, account, agreement);
        return account.getAccountId();
    }

    @Override
    public AccountInfo info(String accountId) throws IOException {
        String userName = "account1@leadersofdigitalsvo.ru";
        ChainIdentity chainIdentity = makeChainIdentity();
        AccountInfo accountInfo = new AccountInfoUseCase().run(chainIdentity, userName, accountId);
        return accountInfo;
    }

    private ChainIdentity makeChainIdentity() {
        return new ChainIdentity("network", "chaincode");
    }
}
