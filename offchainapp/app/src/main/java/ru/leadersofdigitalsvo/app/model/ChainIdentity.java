package ru.leadersofdigitalsvo.app.model;

public class ChainIdentity {

    private String networkName;
    private String chaincodeID;

    public ChainIdentity(String networkName, String chaincodeID) {
        this.networkName = networkName;
        this.chaincodeID = chaincodeID;
    }

    public String getNetworkName() {
        return networkName;
    }

    public ChainIdentity setNetworkName(String networkName) {
        this.networkName = networkName;
        return this;
    }

    public String getChaincodeID() {
        return chaincodeID;
    }

    public ChainIdentity setChaincodeID(String chaincodeID) {
        this.chaincodeID = chaincodeID;
        return this;
    }
}
