package ru.leadersofdigitalsvo.app.domain.support;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Wallet;
import ru.leadersofdigitalsvo.app.dao.chain.support.UserIdentitySupport;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class WalletSetup {

    public static void setup() throws IOException {
        try {
            Wallet wallet = UserIdentitySupport.makeWallet();
            wallet.put("org1admin@org1.leadersofdigitalsvo.ru", makeIdentity("Org1MSP", "org1.leadersofdigitalsvo.ru", "org1admin"));
            wallet.put("org1user1@org1.leadersofdigitalsvo.ru", makeIdentity("Org1MSP", "org1.leadersofdigitalsvo.ru", "org1user1"));
            wallet.put("org2user1@org2.leadersofdigitalsvo.ru", makeIdentity("Org2MSP", "org2.leadersofdigitalsvo.ru", "org2user1"));
        } catch (CertificateException | InvalidKeyException e) {
            throw new IOException(e);
        }
    }

    private static Identity makeIdentity(String mspid, String organisation, String user) throws CertificateException, IOException, InvalidKeyException {
        String label = user + "@" + organisation;
        Path credentialPath = Paths.get("..", "..", "..", "..", "network", "organizations", "peerOrganizations", organisation, "users", label, "msp");
        Path certificatePath = credentialPath.resolve(Paths.get("signcerts", label + ".pem"));
        Path privateKeyPath = credentialPath.resolve(Paths.get("keystore", "priv_sk"));
        X509Certificate certificate = readX509Certificate(certificatePath);
        PrivateKey privateKey = getPrivateKey(privateKeyPath);
        return Identities.newX509Identity(mspid, certificate, privateKey);
    }

    private static X509Certificate readX509Certificate(final Path certificatePath) throws IOException, CertificateException {
        try (Reader certificateReader = Files.newBufferedReader(certificatePath, StandardCharsets.UTF_8)) {
            return Identities.readX509Certificate(certificateReader);
        }
    }

    private static PrivateKey getPrivateKey(final Path privateKeyPath) throws IOException, InvalidKeyException {
        try (Reader privateKeyReader = Files.newBufferedReader(privateKeyPath, StandardCharsets.UTF_8)) {
            return Identities.readPrivateKey(privateKeyReader);
        }
    }
}
