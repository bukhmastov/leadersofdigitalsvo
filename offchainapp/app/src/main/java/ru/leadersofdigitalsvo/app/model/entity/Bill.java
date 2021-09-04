package ru.leadersofdigitalsvo.app.model.entity;

public class Bill {

    private String billId;
    private String accountId;
    private String agreementId;
    private int amount;
    private String state;
    private long date;

    public Bill(String billId, String accountId, String agreementId, int amount, String state, long date) {
        this.billId = billId;
        this.accountId = accountId;
        this.agreementId = agreementId;
        this.amount = amount;
        this.state = state;
        this.date = date;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
