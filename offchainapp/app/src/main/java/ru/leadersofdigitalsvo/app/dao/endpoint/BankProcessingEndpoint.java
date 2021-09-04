package ru.leadersofdigitalsvo.app.dao.endpoint;

public interface BankProcessingEndpoint {

    void withdraw(String accountId, String billId, int amount);
}
