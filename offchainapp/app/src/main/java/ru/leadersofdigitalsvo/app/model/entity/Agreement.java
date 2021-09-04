package ru.leadersofdigitalsvo.app.model.entity;

public class Agreement {

    private String agreementId;
    private String accountId;
    private int period;
    private int periodAmount;
    private int periodPercent;
    private int safeAmount;

    public Agreement(String agreementId, String accountId, int period, int periodAmount, int periodPercent, int safeAmount) {
        this.agreementId = agreementId;
        this.accountId = accountId;
        this.period = period;
        this.periodAmount = periodAmount;
        this.periodPercent = periodPercent;
        this.safeAmount = safeAmount;
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

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(int periodAmount) {
        this.periodAmount = periodAmount;
    }

    public int getPeriodPercent() {
        return periodPercent;
    }

    public void setPeriodPercent(int periodPercent) {
        this.periodPercent = periodPercent;
    }

    public int getSafeAmount() {
        return safeAmount;
    }

    public void setSafeAmount(int safeAmount) {
        this.safeAmount = safeAmount;
    }
}
