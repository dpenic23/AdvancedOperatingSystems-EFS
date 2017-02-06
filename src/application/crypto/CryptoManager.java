package application.crypto;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CryptoManager {

	private static final int SYMMETRIC_KEY_SIZE = 128;

	private KeyGenerator symmetricKeyGenerator;

	public CryptoManager() {
		try {
			symmetricKeyGenerator = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			// ignorable, AES algorithm does exist
		}

		symmetricKeyGenerator.init(SYMMETRIC_KEY_SIZE);
	}

	public SecretKey generateSymmetricKey() {
		return symmetricKeyGenerator.generateKey();
	}

}
