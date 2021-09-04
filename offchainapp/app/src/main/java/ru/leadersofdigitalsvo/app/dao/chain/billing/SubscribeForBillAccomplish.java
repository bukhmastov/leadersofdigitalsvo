package ru.leadersofdigitalsvo.app.dao.chain.billing;

import org.hyperledger.fabric.gateway.Contract;
import ru.leadersofdigitalsvo.common.ChainRegister;

import java.io.IOException;
import java.util.function.Consumer;

import static ru.leadersofdigitalsvo.app.domain.support.ChainNetworkSupport.useNetwork;

public class SubscribeForBillAccomplish {

    public void subscribe(String networkName, String userName, Consumer<String> listener) throws IOException {
        useNetwork(networkName, userName, network -> {
            Contract contract = network.getContract(ChainRegister.billingChaincode, ChainRegister.billingContract);
            contract.addContractListener(event -> {
                byte[] payload = event.getPayload().orElse(null);
                if (payload == null) {
                    return;
                }
                String billId = new String(payload);
                listener.accept(billId);
            }, "bill-accomplish");
            return contract;
        });
    }
}
