package ru.leadersofdigitalsvo.common.model;

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

    @Property()
    private int period;

    @Property()
    private int periodAmount;

    @Property()
    private int periodPercent;

    @Property()
    private int safeAmount;


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

    public int getPeriod() {
        return period;
    }

    public AgreementState setPeriod(int period) {
        this.period = period;
        return this;
    }

    public int getPeriodAmount() {
        return periodAmount;
    }

    public AgreementState setPeriodAmount(int periodAmount) {
        this.periodAmount = periodAmount;
        return this;
    }

    public int getPeriodPercent() {
        return periodPercent;
    }

    public AgreementState setPeriodPercent(int periodPercent) {
        this.periodPercent = periodPercent;
        return this;
    }

    public int getSafeAmount() {
        return safeAmount;
    }

    public AgreementState setSafeAmount(int safeAmount) {
        this.safeAmount = safeAmount;
        return this;
    }

    public static AgreementState createInstance(String agreementId, String accountId, int period, int periodAmount, int periodPercent, int safeAmount) {
        return new AgreementState()
                .setAgreementId(agreementId)
                .setAccountId(accountId)
                .setPeriod(period)
                .setPeriodAmount(periodAmount)
                .setPeriodPercent(periodPercent)
                .setSafeAmount(safeAmount);
    }

    public static byte[] serialize(AgreementState agreementState) {
        return State.serialize(agreementState);
    }

    public static AgreementState deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));
        String agreementId = json.getString("agreementId");
        String accountId = json.getString("accountId");
        int period = json.getInt("period");
        int periodAmount = json.getInt("periodAmount");
        int periodPercent = json.getInt("periodPercent");
        int safeAmount = json.getInt("safeAmount");
        return createInstance(agreementId, accountId, period, periodAmount, periodPercent, safeAmount);
    }
}
