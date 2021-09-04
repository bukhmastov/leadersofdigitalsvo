package ru.leadersofdigitalsvo.app.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leadersofdigitalsvo.app.domain.service.AccountRegistrationService;
import ru.leadersofdigitalsvo.app.model.dto.AccountRegistrationDto;

import java.io.IOException;

@Controller
@RequestMapping("account")
public class AccountController {

    @PostMapping(name = "/register", produces = "application/json")
    public @ResponseBody String register(@RequestBody AccountRegistrationDto dto) throws IOException {
        String accountId = accountRegistrationService.register(dto.account, dto.agreement);
        return accountId;
    }

    @Autowired
    AccountRegistrationService accountRegistrationService;
}
