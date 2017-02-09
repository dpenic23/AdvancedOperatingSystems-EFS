package application.crypto;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class CryptoMethod {

	public static SecretKey generateKey(String algorithm, int keySize) throws CryptoException {
		KeyGenerator keyGenerator = null;

		try {
			keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(keySize);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException("Error during the key generation, no such algorithm: " + algorithm);
		}

		return keyGenerator.generateKey();
	}

	public static SecretKey getSecretKey(String algorithm, String key) throws CryptoException {
		byte[] keyDecoded = null;

		try {
			keyDecoded = Hex.decodeHex(key.toCharArray());
		} catch (DecoderException e) {
			throw new CryptoException("Error during the key decoding");
		}

		return new SecretKeySpec(keyDecoded, 0, keyDecoded.length, algorithm);
	}

	public static byte[] crypt(String algorithm, Key key, int cryptMode, byte[] input) throws CryptoException {
		byte[] crypted = null;

		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(cryptMode, key);
			crypted = cipher.doFinal(input);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new CryptoException(e.getMessage());
		}

		return crypted;
	}

}
