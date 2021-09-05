package ru.leadersofdigitalsvo.app.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.leadersofdigitalsvo.app.domain.service.BillingProcessingService;

import java.io.IOException;

@RestController
@RequestMapping(path = "/processing/bank")
public class BankProcessingController {

    @PostMapping(path = "/accomplish/{id}", produces = "application/json")
    public String accomplish(@PathVariable("id") String billId) throws IOException {
        billingProcessingService.accomplish(billId);
        return "ok";
    }

    @PostMapping(path = "/failed/{id}", produces = "application/json")
    public String failed(@PathVariable("id") String billId) throws IOException {
        billingProcessingService.fail(billId);
        return "ok";
    }

    @Autowired
    BillingProcessingService billingProcessingService;
}
