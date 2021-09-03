package ru.leadersofdigitalsvo.agreementcontract;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;
import ru.leadersofdigitalsvo.agreementcontract.ledgerapi.State;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class Agreement extends State {

    @Property()
    private String agreementId;


    private Agreement() {
        super();
    }

    public String getAgreementId() {
        return agreementId;
    }

    public Agreement setAgreementId(String agreementId) {
        this.agreementId = agreementId;
        return this;
    }


    public static Agreement createInstance(String agreementId) {
        return new Agreement()
                .setAgreementId(agreementId);
    }

    public static byte[] serialize(Agreement agreement) {
        return State.serialize(agreement);
    }

    public static Agreement deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));
        String agreementId = json.getString("agreementId");
        return createInstance(agreementId);
    }
}
