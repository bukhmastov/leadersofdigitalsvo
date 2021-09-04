package ru.leadersofdigitalsvo.app.model.entity;

public class Account {

    private String accountId;
    private String state;
    private int value;

    public Account(String accountId, String state, int value) {
        this.accountId = accountId;
        this.state = state;
        this.value = value;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
