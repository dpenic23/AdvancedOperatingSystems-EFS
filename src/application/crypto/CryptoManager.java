package application.crypto;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import application.file.FileManager;

public class CryptoManager {

	private static final String SYMMETRIC_ALGORITHM = "AES";
	private static final String ASYMMETRIC_ALGORITHM = "RSA";

	private static final int SYMMETRIC_KEY_SIZE = 128;

	private KeyGenerator symmetricKeyGenerator;
	private KeyPairGenerator asymmetricKeyGenerator;

	private FileManager fileManager = new FileManager();

	public CryptoManager() {
		try {
			symmetricKeyGenerator = KeyGenerator.getInstance(SYMMETRIC_ALGORITHM);
			asymmetricKeyGenerator = KeyPairGenerator.getInstance(ASYMMETRIC_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			// ignorable, algorithms are not defined by user
		}

		symmetricKeyGenerator.init(SYMMETRIC_KEY_SIZE);
	}

	public void generateSymmetricKey(String keyFilePath) throws IOException {
		String key = Hex.encodeHexString(symmetricKeyGenerator.generateKey().getEncoded());

		CryptoProperties properties = new CryptoProperties();

		properties.addProperty(CryptoProperties.DESCRIPTION, "Secret Key");
		properties.addProperty(CryptoProperties.METHOD, SYMMETRIC_ALGORITHM);
		properties.addProperty(CryptoProperties.SECRET_KEY, key);

		fileManager.writePropertiesToFile(properties, keyFilePath);
	}

	public void encryptFileSymmetric(String inputFilePath, String outputFilePath, String keyFilePath)
			throws IOException, CryptoException {
		String inputFileContent = fileManager.readFile(inputFilePath);

		CryptoProperties keyProperties = fileManager.readPropertiesFromFile(keyFilePath);
		String key = keyProperties.valueAssembled(CryptoProperties.SECRET_KEY);

		byte[] decodedKey = {};
		try {
			decodedKey = Hex.decodeHex(key.toCharArray());
		} catch (DecoderException e) {
			throw new CryptoException();
		}
		SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, SYMMETRIC_ALGORITHM);
		
		byte[] encrypted = crypt(SYMMETRIC_ALGORITHM, secretKey, Cipher.ENCRYPT_MODE, inputFileContent.getBytes());
		String encryptedText = Base64.getEncoder().encodeToString(encrypted);
		
		CryptoProperties properties = new CryptoProperties();
		
		properties.addProperty(CryptoProperties.DESCRIPTION, "Crypted file");
		properties.addProperty(CryptoProperties.METHOD, SYMMETRIC_ALGORITHM);
		properties.addProperty(CryptoProperties.FILE_NAME, inputFilePath);
		properties.addProperty(CryptoProperties.DATA, encryptedText);
		
		fileManager.writePropertiesToFile(properties, outputFilePath);
		
		/*
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(SYMMETRIC_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] byteCipherText = cipher.doFinal(inputFileContent.getBytes());

			String s = Base64.getEncoder().encodeToString(byteCipherText);

			//////////
			byte[] bytes = Base64.getDecoder().decode(s);
			cipher = Cipher.getInstance(SYMMETRIC_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] res = cipher.doFinal(bytes);

			System.out.println(new String(res));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		*/

	}

	public void decryptFileSymmetric(String inputFilePath, String outputFilePath, String keyFilePath)
			throws IOException, CryptoException {

	}

	private static byte[] crypt(String algorithm, Key key, int cryptMode, byte[] input) throws CryptoException {
		byte[] crypted = {};

		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(cryptMode, key);
			crypted = cipher.doFinal(input);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new CryptoException();
		}

		return crypted;
	}
	
}
