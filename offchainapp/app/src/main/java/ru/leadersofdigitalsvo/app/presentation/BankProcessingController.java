package ru.leadersofdigitalsvo.app.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.leadersofdigitalsvo.app.domain.service.BillingProcessingService;

@Controller
@RequestMapping("/processing/bank")
public class BankProcessingController {

    @PostMapping(name = "/accomplish/{id}", produces = "application/json")
    public String accomplish(@PathVariable("id") String billId) {
        billingProcessingService.accomplish(billId);
        return "ok";
    }

    @PostMapping(name = "/fail/{id}", produces = "application/json")
    public String fail(@PathVariable("id") String billId) {
        billingProcessingService.fail(billId);
        return "ok";
    }

    @Autowired
    BillingProcessingService billingProcessingService;
}
