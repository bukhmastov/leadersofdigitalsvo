package ru.leadersofdigitalsvo.app.dao.chain.support;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import ru.leadersofdigitalsvo.app.model.UserIdentity;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserIdentitySupport {

    private static final Path walletPath = Paths.get(".", "wallet");

    public UserIdentity makeUserIdentity(String userName) throws IOException {
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        return new UserIdentity(wallet, userName);
    }
}
