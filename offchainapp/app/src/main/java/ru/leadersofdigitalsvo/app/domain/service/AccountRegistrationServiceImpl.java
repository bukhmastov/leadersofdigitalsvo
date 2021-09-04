package ru.leadersofdigitalsvo.app.domain.service;

import org.springframework.stereotype.Service;
import ru.leadersofdigitalsvo.app.dao.chain.account.AccountInfoUseCase;
import ru.leadersofdigitalsvo.app.dao.chain.account.RegisterAccountUseCase;
import ru.leadersofdigitalsvo.app.model.entity.Account;
import ru.leadersofdigitalsvo.app.model.entity.AccountInfo;
import ru.leadersofdigitalsvo.app.model.entity.Agreement;

import java.io.IOException;

import static ru.leadersofdigitalsvo.app.domain.util.EntityId.makeEntityId;

@Service
public class AccountRegistrationServiceImpl implements AccountRegistrationService {

    @Override
    public String register(Account account, Agreement agreement) throws IOException {
        String userName = "org2@leadersofdigitalsvo.ru";
        String networkName = "network";
        account.setAccountId(makeEntityId());
        agreement.setAccountId(account.getAccountId());
        agreement.setAgreementId(makeEntityId());
        new RegisterAccountUseCase().run(networkName, userName, account, agreement);
        return account.getAccountId();
    }

    @Override
    public AccountInfo info(String accountId) throws IOException {
        String userName = "org2@leadersofdigitalsvo.ru";
        String networkName = "network";
        AccountInfo accountInfo = new AccountInfoUseCase().run(networkName, userName, accountId);
        return accountInfo;
    }
}
