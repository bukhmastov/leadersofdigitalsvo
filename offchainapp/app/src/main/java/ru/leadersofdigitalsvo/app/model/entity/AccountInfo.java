package ru.leadersofdigitalsvo.app.model.entity;

import java.util.List;

public class AccountInfo {

    private Account account;
    private Agreement agreement;
    private List<Bill> billList;

    public AccountInfo(Account account, Agreement agreement, List<Bill> billList) {
        this.account = account;
        this.agreement = agreement;
        this.billList = billList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }
}
