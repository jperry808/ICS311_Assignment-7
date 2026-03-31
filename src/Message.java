import java.util.HashMap;
import java.util.Map;

// Stores the encrypted AES key (RSA), the encrypted message body (AES-GCM),
public class Message {
    public final String senderId;   // Who sent the message
    public final String receiverId; // Who should receive the message
    public final Map<String, String> metadata = new HashMap<>();

    // Base64-encoded fields to make them text-friendly
    public final String rsaEncryptedAesKeyB64; // AES key encrypted with receiver's RSA public key
    public final String ivB64;                 // AES-GCM initialization vector
    public final String ciphertextB64;         // Encrypted message body

    // Constructor to initialize the message
    public Message(String senderId, String receiverId,
                   String rsaEncryptedAesKeyB64, String ivB64, String ciphertextB64) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.rsaEncryptedAesKeyB64 = rsaEncryptedAesKeyB64;
        this.ivB64 = ivB64;
        this.ciphertextB64 = ciphertextB64;

        // Add default metadata
        metadata.put("crypto", "rsa-oaep+aes-gcm"); 
        metadata.put("note", "AES key is RSA-encrypted; body is AES-GCM");
    }
}
