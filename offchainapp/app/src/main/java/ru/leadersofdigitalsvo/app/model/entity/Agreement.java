package ru.leadersofdigitalsvo.app.model.entity;

public class Agreement {

    private String agreementId;
    private String accountId;

    public Agreement(String agreementId) {
        this.agreementId = agreementId;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
