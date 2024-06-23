package smartcontact.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AESSecurity {
	private static final Logger LOGGER = LoggerFactory.getLogger(AESSecurity.class);
	private static final String AESCIPHER = "AES/ECB/PKCS5Padding";
	private static final String AES = "AES";


	public static String encrypt(String input, String secretKey, String username) {
		try {
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
			SecretKeySpec skey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES);
			Cipher cipher = Cipher.getInstance(AESCIPHER);
			cipher.init(Cipher.DECRYPT_MODE, skey);

			byte[] output = cipher.doFinal(Base64.decodeBase64(input));
			String decrypted = new String(output, StandardCharsets.UTF_8);

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
}
