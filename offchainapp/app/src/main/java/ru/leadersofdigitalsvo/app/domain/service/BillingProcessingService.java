package ru.leadersofdigitalsvo.app.domain.service;

import java.io.IOException;

public interface BillingProcessingService {

    void accomplish(String billId) throws IOException;

    void fail(String billId) throws IOException;
}
