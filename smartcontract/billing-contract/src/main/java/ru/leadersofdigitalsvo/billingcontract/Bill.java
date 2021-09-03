package ru.leadersofdigitalsvo.billingcontract;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;
import ru.leadersofdigitalsvo.billingcontract.ledgerapi.State;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class Bill extends State {

    @Property()
    private String billId;

    @Property()
    private String accountId;

    @Property()
    private String agreementId;


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


    public static Bill createInstance(String billId, String accountId, String agreementId) {
        return new Bill()
                .setBillId(billId)
                .setAccountId(accountId)
                .setAgreementId(agreementId);
    }

    public static byte[] serialize(Bill bill) {
        return State.serialize(bill);
    }

    public static Bill deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));
        String billId = json.getString("billId");
        String accountId = json.getString("accountId");
        String agreementId = json.getString("agreementId");
        return createInstance(billId, accountId, agreementId);
    }
}
