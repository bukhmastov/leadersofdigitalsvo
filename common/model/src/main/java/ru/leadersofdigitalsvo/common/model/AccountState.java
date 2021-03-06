package ru.leadersofdigitalsvo.common.model;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;
import org.json.JSONPropertyIgnore;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class AccountState extends State {

    public static final String STATE_ACTIVE = "active";
    public static final String STATE_CLOSED = "closed";

    @Property()
    private String accountId;

    @Property()
    private String mspId;

    @Property()
    private String state;

    @Property()
    private int value;

    @Property()
    private int lastValueChangeTime;

    @Property()
    private String agreementId;

    private AccountState() {
        super();
    }

    public String getAccountId() {
        return accountId;
    }

    public AccountState setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getMspId() {
        return mspId;
    }

    public AccountState setMspId(String mspId) {
        this.mspId = mspId;
        return this;
    }

    public String getState() {
        return state;
    }

    public AccountState setState(String state) {
        this.state = state;
        return this;
    }

    public int getValue() {
        return value;
    }

    public AccountState setValue(int value) {
        this.value = value;
        return this;
    }

    public int getLastValueChangeTime() {
        return lastValueChangeTime;
    }

    public AccountState setLastValueChangeTime(int lastValueChangeTime) {
        this.lastValueChangeTime = lastValueChangeTime;
        return this;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public AccountState setAgreementId(String agreementId) {
        this.agreementId = agreementId;
        return this;
    }

    @JSONPropertyIgnore()
    public boolean isActive() {
        return this.state.equals(STATE_ACTIVE);
    }

    @JSONPropertyIgnore()
    public boolean isClosed() {
        return this.state.equals(STATE_CLOSED);
    }

    public static AccountState createInstance(String accountId, String mspId, String state, int value, int lastValueChangeTime) {
        return new AccountState()
                .setAccountId(accountId)
                .setMspId(mspId)
                .setState(state)
                .setValue(value)
                .setLastValueChangeTime(lastValueChangeTime);
    }

    public static byte[] serialize(AccountState accountState) {
        return State.serialize(accountState);
    }

    public static AccountState deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));
        String accountId = json.getString("accountId");
        String mspId = json.getString("mspId");
        String state = json.getString("state");
        int value = json.getInt("value");
        int lastValueChangeTime = json.getInt("lastValueChangeTime");
        String agreementId = json.getString("agreementId");
        return createInstance(accountId, mspId, state, value, lastValueChangeTime)
                .setAgreementId(agreementId);
    }
}
