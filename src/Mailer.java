import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Mailer {

    public static Message sendEncrypted(Person sender, Person receiver, String plaintext) throws Exception {
        // A new AES key is generated
        SecretKey aesKey = AESUtil.newAesKey();

        // Encrypt message body with AES-GCM
        AESUtil.AesGcmPayload payload = AESUtil.encrypt(
                plaintext.getBytes(StandardCharsets.UTF_8), aesKey);

        // Encrypt the AES key with receiver's public key
        byte[] aesKeyBytes = aesKey.getEncoded();
        byte[] rsaEncryptedKey = RSAUtil.encryptOAEP(aesKeyBytes, receiver.getPublicKey());

        // Convert binary data to Base64 strings
        String kB64 = Base64.getEncoder().encodeToString(rsaEncryptedKey);
        String ivB64 = Base64.getEncoder().encodeToString(payload.iv);
        String ctB64 = Base64.getEncoder().encodeToString(payload.ciphertext);

        return new Message(sender.name, receiver.name, kB64, ivB64, ctB64);
    }

    // Decrypt's an encrypted message
    public static String receiveDecrypted(Person receiver, Message msg) throws Exception {
        if (!receiver.name.equals(msg.receiverId))
            throw new IllegalArgumentException("Wrong receiver");

        // Decode Base64 strings back to byte arrays
        byte[] encKey = Base64.getDecoder().decode(msg.rsaEncryptedAesKeyB64);
        byte[] iv = Base64.getDecoder().decode(msg.ivB64);
        byte[] ct = Base64.getDecoder().decode(msg.ciphertextB64);

        // RSA-decrypt the AES key
        byte[] aesKeyBytes = RSAUtil.decryptOAEP(encKey, receiver.getPrivateKey());
        SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");

        // AES-decrypt the body
        byte[] pt = AESUtil.decrypt(new AESUtil.AesGcmPayload(iv, ct), aesKey);
        return new String(pt, StandardCharsets.UTF_8);
    }
}
