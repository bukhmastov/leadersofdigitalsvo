package ru.leadersofdigitalsvo.app.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.leadersofdigitalsvo.app.domain.service.BillingProcessingService;

import java.io.IOException;

@Controller
public class BankProcessingController {

    @PostMapping(name = "/processing/bank/accomplish/{id}", produces = "application/json")
    public String accomplish(@PathVariable("id") String billId) throws IOException {
        billingProcessingService.accomplish(billId);
        return "ok";
    }

    @PostMapping(name = "/processing/bank/fail/{id}", produces = "application/json")
    public String fail(@PathVariable("id") String billId) throws IOException {
        billingProcessingService.fail(billId);
        return "ok";
    }

    @Autowired
    BillingProcessingService billingProcessingService;
}
