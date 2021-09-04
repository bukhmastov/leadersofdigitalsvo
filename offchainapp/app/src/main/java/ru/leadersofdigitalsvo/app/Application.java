package ru.leadersofdigitalsvo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.leadersofdigitalsvo.app.domain.support.WalletSetup;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        WalletSetup.setup();
        SpringApplication.run(Application.class, args);
    }
}
