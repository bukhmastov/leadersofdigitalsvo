package ru.leadersofdigitalsvo.accountcontract;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;
import ru.leadersofdigitalsvo.accountcontract.ledgerapi.State;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class Account extends State {

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

    private Account() {
        super();
    }

    public String getAccountId() {
        return accountId;
    }

    public Account setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getMspId() {
        return mspId;
    }

    public Account setMspId(String mspId) {
        this.mspId = mspId;
        return this;
    }

    public String getState() {
        return state;
    }

    public Account setState(String state) {
        this.state = state;
        return this;
    }

    public int getValue() {
        return value;
    }

    public Account setValue(int value) {
        this.value = value;
        return this;
    }

    public int getLastValueChangeTime() {
        return lastValueChangeTime;
    }

    public Account setLastValueChangeTime(int lastValueChangeTime) {
        this.lastValueChangeTime = lastValueChangeTime;
        return this;
    }

    @Override
    public String toString() {
        return "Account::" + key + " " + getAccountId() + " " + getValue() + " " + getLastValueChangeTime();
    }

    public static Account createInstance(String accountId, String mspId, String state, int value, int lastValueChangeTime) {
        return new Account()
                .setAccountId(accountId)
                .setMspId(mspId)
                .setState(state)
                .setValue(value)
                .setLastValueChangeTime(lastValueChangeTime);
    }

    public static byte[] serialize(Account account) {
        return State.serialize(account);
    }

    public static Account deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));
        String accountId = json.getString("accountId");
        String mspId = json.getString("mspId");
        String state = json.getString("state");
        int value = json.getInt("value");
        int lastValueChangeTime = json.getInt("lastValueChangeTime");
        return createInstance(accountId, mspId, state, value, lastValueChangeTime);
    }
}
