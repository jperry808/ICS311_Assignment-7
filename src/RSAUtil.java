import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class RSAUtil {

    /**
     * Generates a new RSA public/private keypair.
     * @param bits Size of the key (2048 recommended minimum)
     */
    public static KeyPair generateKeyPair(int bits) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(bits);
        return kpg.generateKeyPair();
    }

    // Encrypt the AES key
    public static byte[] encryptOAEP(byte[] data, PublicKey pub) throws Exception {
        Cipher c = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        OAEPParameterSpec oaep = new OAEPParameterSpec(
                "SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
        c.init(Cipher.ENCRYPT_MODE, pub, oaep);
        return c.doFinal(data);
    }

    // Decrypt the AES key
    public static byte[] decryptOAEP(byte[] data, PrivateKey priv) throws Exception {
        Cipher c = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        OAEPParameterSpec oaep = new OAEPParameterSpec(
                "SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
        c.init(Cipher.DECRYPT_MODE, priv, oaep);
        return c.doFinal(data);
    }
}
