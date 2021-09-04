package ru.leadersofdigitalsvo.app.model;

import org.hyperledger.fabric.gateway.Wallet;

public class UserChainIdentity {

    private Wallet wallet;
    private String userName;

    public UserChainIdentity(Wallet wallet, String userName) {
        this.wallet = wallet;
        this.userName = userName;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public UserChainIdentity setWallet(Wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserChainIdentity setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
