package ru.leadersofdigitalsvo.app.model.dto;

public class AccountLoginDto {

    public String accountId;
    public String password;

    public AccountLoginDto(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }
}
