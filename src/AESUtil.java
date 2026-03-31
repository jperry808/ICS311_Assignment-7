import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class AESUtil {

    // Inner class to store both IV and ciphertext together
    public static class AesGcmPayload {
        public final byte[] iv;         // Random for each encryption
        public final byte[] ciphertext; // Encrypted data

        public AesGcmPayload(byte[] iv, byte[] ciphertext) {
            this.iv = iv;
            this.ciphertext = ciphertext;
        }
    }

    // Generates a new random AES key
    public static SecretKey newAesKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);
        return kg.generateKey();
    }

    // Generates a random byte array for IVs.
    public static byte[] randomBytes(int n) {
        byte[] b = new byte[n];
        new SecureRandom().nextBytes(b);
        return b;
    }

    /**
     * Encrypts plaintext using AES in GCM mode.
     * @param plaintext The data to encrypt
     * @param key The AES secret key
     */
    public static AesGcmPayload encrypt(byte[] plaintext, SecretKey key) throws Exception {
        byte[] iv = randomBytes(12); // 96-bit IV is standard for GCM
        Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv); // 128-bit authentication tag
        c.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] ct = c.doFinal(plaintext);
        return new AesGcmPayload(iv, ct);
    }

    /**
     * Decrypts ciphertext using AES in GCM mode.
     * @param payload The IV and ciphertext
     * @param key The AES secret key
     */
    public static byte[] decrypt(AesGcmPayload payload, SecretKey key) throws Exception {
        Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, payload.iv);
        c.init(Cipher.DECRYPT_MODE, key, spec);
        return c.doFinal(payload.ciphertext);
    }
}
