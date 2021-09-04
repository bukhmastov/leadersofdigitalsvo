package ru.leadersofdigitalsvo.common.model;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;
import org.json.JSONPropertyIgnore;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class BillState extends State {

    public static final String STATE_ISSUED = "issued";
    public static final String STATE_ACCOMPLISHED = "accomplished";
    public static final String STATE_FAILED = "failed";

    @Property()
    private String billId;

    @Property()
    private String accountId;

    @Property()
    private String agreementId;

    @Property()
    private int amount;

    @Property()
    private String state;

    @Property()
    private long date;


    private BillState() {
        super();
    }

    public String getBillId() {
        return billId;
    }

    public BillState setBillId(String billId) {
        this.billId = billId;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public BillState setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public BillState setAgreementId(String agreementId) {
        this.agreementId = agreementId;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public BillState setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getState() {
        return state;
    }

    public BillState setState(String state) {
        this.state = state;
        return this;
    }

    public long getDate() {
        return date;
    }

    public BillState setDate(long date) {
        this.date = date;
        return this;
    }

    @JSONPropertyIgnore()
    public boolean isIssued() {
        return this.state.equals(STATE_ISSUED);
    }

    @JSONPropertyIgnore()
    public boolean isAccomplished() {
        return this.state.equals(STATE_ACCOMPLISHED);
    }

    @JSONPropertyIgnore()
    public boolean isFailed() {
        return this.state.equals(STATE_FAILED);
    }

    public static BillState createInstance(String billId, String accountId, String agreementId, int amount, String state, long date) {
        return new BillState()
                .setBillId(billId)
                .setAccountId(accountId)
                .setAgreementId(agreementId)
                .setAmount(amount)
                .setState(state)
                .setDate(date);
    }

    public static byte[] serialize(BillState billState) {
        return State.serialize(billState);
    }

    public static BillState deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));
        String billId = json.getString("billId");
        String accountId = json.getString("accountId");
        String agreementId = json.getString("agreementId");
        int amount = json.getInt("amount");
        String state = json.getString("state");
        long date = json.getLong("date");
        return createInstance(billId, accountId, agreementId, amount, state, date);
    }
}
