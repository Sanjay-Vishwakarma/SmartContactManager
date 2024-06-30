package smartcontact.security;

import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AESSecurity {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESSecurity.class);
    private static final String AESCIPHER = "AES/ECB/PKCS5Padding";
    private static final String AES = "AES";

    @Value("${secretKey}")
    public static String secKey;

    private static String staticKey = "a8f5f167f44f4964e6c998dee827110c";

//    @PostConstruct
//    private void init(){
//        staticKey=this.secKey;
//    }

    public static String encrypt(String input, String secretKey, String username) {
        try {
            if (secretKey.length() != 32) {
                throw new IllegalArgumentException("Secret key must be 32 bytes for AES-256.");
            }

            SecretKeySpec skey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES);
            Cipher cipher = Cipher.getInstance(AESCIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, skey);

            // Combine input with username
            String combinedInput = username + ":" + input;

            byte[] crypted = cipher.doFinal(combinedInput.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(crypted);
        } catch (Exception e) {
            LOGGER.error("AESSecurity_encrypt", e);
            return "";
        }
    }

    public static String decrypt(String input, String secretKey, String username) {
        try {
            if (secretKey.length() != 32) {
                throw new IllegalArgumentException("Secret key must be 32 bytes for AES-256.");
            }

            SecretKeySpec skey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES);
            Cipher cipher = Cipher.getInstance(AESCIPHER);
            cipher.init(Cipher.DECRYPT_MODE, skey);

            byte[] decodedInput = Base64.decodeBase64(input);
            byte[] output = cipher.doFinal(decodedInput);
            String decrypted = new String(output, StandardCharsets.UTF_8);
            System.out.println("decrypted = " + decrypted);

            // Extract the username and the actual data
            if (decrypted.startsWith(username + ":")) {
                return decrypted.substring((username + ":").length());
            } else {
                LOGGER.error("AESSecurity_decrypt: Username does not match");
                return "";
            }
        } catch (Exception e) {
            LOGGER.error("AESSecurity_decrypt", e);
            return "";
        }
    }


    public static void main(String[] args) {

        String username = "testUser";
        String password = "mySecretPassword";

//      Encrypt the password
        String encrypted = AESSecurity.encrypt(password, staticKey, username);
        System.out.println("Encrypted: " + encrypted);

        String decrp= "yhfR/uRNdc127+hRgE8lhhp7GAw5DBQoMRz+5gdqaOI=";

        // Decrypt the password
        String decrypted = AESSecurity.decrypt(decrp, staticKey, "testUser");
        System.out.println("Decrypted: " + decrypted);
    }
}
