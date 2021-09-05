package ru.leadersofdigitalsvo.app.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leadersofdigitalsvo.app.domain.service.AccountRegistrationService;
import ru.leadersofdigitalsvo.app.model.dto.AccountRegistrationDto;
import ru.leadersofdigitalsvo.app.model.entity.AccountInfo;

import java.io.IOException;

@Controller
public class AccountController {

    @PostMapping(name = "/account/register", produces = "application/json")
    public @ResponseBody String register(@RequestBody AccountRegistrationDto dto) throws IOException {
        String accountId = accountRegistrationService.register(dto.account, dto.agreement);
        return accountId;
    }

    @GetMapping(name = "/account/info/{id}", produces = "application/json")
    public @ResponseBody AccountInfo info(@PathVariable("id") String accountId) throws IOException {
        AccountInfo accountInfo = accountRegistrationService.info(accountId);
        return accountInfo;
    }

    @Autowired
    AccountRegistrationService accountRegistrationService;
}
