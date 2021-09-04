package ru.leadersofdigitalsvo.billingcontract;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;
import org.json.JSONPropertyIgnore;
import ru.leadersofdigitalsvo.billingcontract.ledgerapi.State;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class Bill extends State {

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


    private Bill() {
        super();
    }

    public String getBillId() {
        return billId;
    }

    public Bill setBillId(String billId) {
        this.billId = billId;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public Bill setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public Bill setAgreementId(String agreementId) {
        this.agreementId = agreementId;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public Bill setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getState() {
        return state;
    }

    public Bill setState(String state) {
        this.state = state;
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

    public static Bill createInstance(String billId, String accountId, String agreementId, int amount, String state) {
        return new Bill()
                .setBillId(billId)
                .setAccountId(accountId)
                .setAgreementId(agreementId)
                .setAmount(amount)
                .setState(state);
    }

    public static byte[] serialize(Bill bill) {
        return State.serialize(bill);
    }

    public static Bill deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));
        String billId = json.getString("billId");
        String accountId = json.getString("accountId");
        String agreementId = json.getString("agreementId");
        int amount = json.getInt("amount");
        String state = json.getString("state");
        return createInstance(billId, accountId, agreementId, amount, state);
    }
}
