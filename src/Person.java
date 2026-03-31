import java.security.KeyPair;

/**
 * Each person has:
 * - An ID (like a username)
 * - An RSA public/private keypair for encryption and decryption
 */
public class Person {
    public final String name;          // The person's name
    public final KeyPair rsaKeyPair;   // Their RSA keypair

    // When a new Person is created RSA key's are generated
    public Person(String name) throws Exception {
        this.name = name;
        this.rsaKeyPair = RSAUtil.generateKeyPair(2048);
    }

    // Get the public key
    public java.security.PublicKey getPublicKey() {
        return rsaKeyPair.getPublic();
    }

    // Get the private key
    public java.security.PrivateKey getPrivateKey() {
        return rsaKeyPair.getPrivate();
    }
}
