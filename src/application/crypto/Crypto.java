package application.crypto;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Crypto {

	private KeyGenerator symmetricKeyGenerator;

	public Crypto() {
		try {
			symmetricKeyGenerator = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			// ignorable, AES algorithm does exist
		}
	}

	public SecretKey generateSymmetricKey() {
		return symmetricKeyGenerator.generateKey();
	}

}
