package ru.leadersofdigitalsvo.app.domain.service;

import ru.leadersofdigitalsvo.app.model.entity.Account;
import ru.leadersofdigitalsvo.app.model.entity.AccountInfo;
import ru.leadersofdigitalsvo.app.model.entity.Agreement;

import java.io.IOException;

public interface AccountRegistrationService {

    String register(Account account, Agreement agreement) throws IOException;

    AccountInfo info(String accountId) throws IOException;
}
