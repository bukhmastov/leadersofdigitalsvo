package ru.leadersofdigitalsvo.app.model.dto;

import ru.leadersofdigitalsvo.app.model.entity.Account;
import ru.leadersofdigitalsvo.app.model.entity.Agreement;

public class AccountRegistrationDto {

    public Account account;
    public Agreement agreement;

    public AccountRegistrationDto(Account account, Agreement agreement) {
        this.account = account;
        this.agreement = agreement;
    }
}
