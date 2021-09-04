package ru.leadersofdigitalsvo.app.model.chain;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class AgreementState extends State {

    @Property()
    private String agreementId;

    @Property()
    private String accountId;


    private AgreementState() {
        super();
    }

    public String getAgreementId() {
        return agreementId;
    }

    public AgreementState setAgreementId(String agreementId) {
        this.agreementId = agreementId;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public AgreementState setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public static AgreementState createInstance(String agreementId, String accountId) {
        return new AgreementState()
                .setAgreementId(agreementId)
                .setAccountId(accountId);
    }

    public static byte[] serialize(AgreementState agreement) {
        return State.serialize(agreement);
    }

    public static AgreementState deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));
        String agreementId = json.getString("agreementId");
        String accountId = json.getString("accountId");
        return createInstance(agreementId, accountId);
    }
}
