package ru.leadersofdigitalsvo.app.model;

import org.hyperledger.fabric.gateway.Wallet;

public class UserIdentity {

    private Wallet wallet;
    private String userName;

    public UserIdentity(Wallet wallet, String userName) {
        this.wallet = wallet;
        this.userName = userName;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public UserIdentity setWallet(Wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserIdentity setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
