package ru.leadersofdigitalsvo.app.dao.endpoint;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankProcessingEndpointImpl implements BankProcessingEndpoint {

    private static final String url = "http://localhost:9090/processing/bank";

    @Override
    public void withdraw(String accountId, String billId, int amount) {
        RestTemplate restTemplate = new RestTemplate();
        String currentUrl = new StringBuilder(url)
                .append("?account=").append(accountId)
                .append("&bill=").append(billId)
                .append("&amount=").append(amount)
                .toString();
        restTemplate.getForEntity(currentUrl, String.class);
    }
}
