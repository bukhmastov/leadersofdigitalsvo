package ru.leadersofdigitalsvo.app.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.leadersofdigitalsvo.app.domain.service.AccountRegistrationService;
import ru.leadersofdigitalsvo.app.model.dto.AccountLoginDto;
import ru.leadersofdigitalsvo.app.model.dto.AccountRegistrationDto;
import ru.leadersofdigitalsvo.app.model.entity.AccountInfo;

import java.io.IOException;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    @PostMapping(path = "/register", produces = "application/json")
    public @ResponseBody String register(@RequestBody AccountRegistrationDto dto) throws IOException {
        String accountId = accountRegistrationService.register(dto.account, dto.agreement);
        return accountId;
    }

    @PostMapping(path = "/login", produces = "application/json")
    public @ResponseBody String login(@RequestBody AccountLoginDto dto) throws IOException {
        String accountId = accountRegistrationService.login(dto.accountId, dto.password);
        return accountId;
    }

    @GetMapping(path = "/info/{id}", produces = "application/json")
    public @ResponseBody AccountInfo info(@PathVariable("id") String accountId) throws IOException {
        AccountInfo accountInfo = accountRegistrationService.info(accountId);
        return accountInfo;
    }

    @Autowired
    AccountRegistrationService accountRegistrationService;
}
