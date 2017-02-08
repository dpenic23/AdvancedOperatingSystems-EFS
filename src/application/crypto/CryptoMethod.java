package application.crypto;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CryptoMethod {

	public static SecretKey generateKey(String algorithm, int keySize) throws CryptoException {
		KeyGenerator keyGenerator = null;

		try {
			keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(keySize);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException("Error occured during the key generation, no such algorithm: " + algorithm);
		}

		return keyGenerator.generateKey();
	}

}
