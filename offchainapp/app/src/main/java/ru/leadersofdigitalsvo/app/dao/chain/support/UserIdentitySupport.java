package ru.leadersofdigitalsvo.app.dao.chain.support;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import ru.leadersofdigitalsvo.app.model.UserChainIdentity;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserIdentitySupport {

    private static final Path walletPath = Paths.get(".", "wallet");

    public static Wallet makeWallet() throws IOException {
        return Wallets.newFileSystemWallet(walletPath);
    }

    public static UserChainIdentity makeUserIdentity(String userName) throws IOException {
        Wallet wallet = makeWallet();
        return new UserChainIdentity(wallet, userName);
    }
}
