package application.crypto;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
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
import org.apache.commons.codec.digest.DigestUtils;

import application.file.FileManager;

public class CryptoManager {

	private static final String SYMMETRIC_ALGORITHM = "AES";
	private static final String ASYMMETRIC_ALGORITHM = "RSA";
	private static final String HASH_METHOD = "SHA-1";

	private static final int SYMMETRIC_KEY_SIZE = 128;

	private KeyGenerator symmetricKeyGenerator;
	private KeyPairGenerator asymmetricKeyGenerator;

	private FileManager fileManager = new FileManager();

	public CryptoManager() {
		try {
			symmetricKeyGenerator = KeyGenerator.getInstance(SYMMETRIC_ALGORITHM);
			asymmetricKeyGenerator = KeyPairGenerator.getInstance(ASYMMETRIC_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			// ignorable, algorithms are not defined by the user
		}

		symmetricKeyGenerator.init(SYMMETRIC_KEY_SIZE);
	}

	public void generateSymmetricKey(String keyFilePath) throws IOException {
		String key = Hex.encodeHexString(symmetricKeyGenerator.generateKey().getEncoded());

		CryptoProperties keyProperties = new CryptoProperties();

		keyProperties.addProperty(CryptoProperties.DESCRIPTION, "Secret key");
		keyProperties.addProperty(CryptoProperties.METHOD, SYMMETRIC_ALGORITHM);
		keyProperties.addProperty(CryptoProperties.SECRET_KEY, key);

		fileManager.writePropertiesToFile(keyProperties, keyFilePath);
	}

	public void generateAsymmetricKeys(String publicKeyFilePath, String privateKeyFilePath, int keySize)
			throws IOException {
		asymmetricKeyGenerator.initialize(keySize);

		KeyPair keyPair = asymmetricKeyGenerator.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		CryptoProperties publicKeyProperties = new CryptoProperties();

		publicKeyProperties.addProperty(CryptoProperties.DESCRIPTION, "Public key");
		publicKeyProperties.addProperty(CryptoProperties.METHOD, ASYMMETRIC_ALGORITHM);
		publicKeyProperties.addProperty(CryptoProperties.KEY_LENGTH,
				Integer.toHexString(publicKey.getModulus().toString(16).length()));
		publicKeyProperties.addProperty(CryptoProperties.MODULUS, publicKey.getModulus().toString(16));
		publicKeyProperties.addProperty(CryptoProperties.PUBLIC_EXP, publicKey.getPublicExponent().toString(16));

		CryptoProperties privateKeyProperties = new CryptoProperties();

		privateKeyProperties.addProperty(CryptoProperties.DESCRIPTION, "Private key");
		privateKeyProperties.addProperty(CryptoProperties.METHOD, ASYMMETRIC_ALGORITHM);
		privateKeyProperties.addProperty(CryptoProperties.KEY_LENGTH,
				Integer.toHexString(privateKey.getModulus().toString(16).length()));
		privateKeyProperties.addProperty(CryptoProperties.MODULUS, privateKey.getModulus().toString(16));
		privateKeyProperties.addProperty(CryptoProperties.PRIVATE_EXP, privateKey.getPrivateExponent().toString(16));

		fileManager.writePropertiesToFile(publicKeyProperties, publicKeyFilePath);
		fileManager.writePropertiesToFile(privateKeyProperties, privateKeyFilePath);

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
	}

	public void encryptFileAsymmetric(String inputFilePath, String outputFilePath, String privateKeyFilePath)
			throws IOException, CryptoException {
		String inputFileContent = fileManager.readFile(inputFilePath);

		CryptoProperties keyProperties = fileManager.readPropertiesFromFile(privateKeyFilePath);
		String keyModulus = keyProperties.valueAssembled(CryptoProperties.MODULUS);
		String keyExp = keyProperties.valueAssembled(CryptoProperties.PRIVATE_EXP);
		RSAPrivateKeySpec privateKey = new RSAPrivateKeySpec(new BigInteger(keyModulus, 16),
				new BigInteger(keyExp, 16));

		PrivateKey key = null;
		try {
			key = KeyFactory.getInstance(ASYMMETRIC_ALGORITHM).generatePrivate(privateKey);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new CryptoException();
		}

		byte[] encrypted = crypt(ASYMMETRIC_ALGORITHM, key, Cipher.ENCRYPT_MODE, inputFileContent.getBytes());
		String encryptedText = Base64.getEncoder().encodeToString(encrypted);

		CryptoProperties properties = new CryptoProperties();

		properties.addProperty(CryptoProperties.DESCRIPTION, "Crypted file");
		properties.addProperty(CryptoProperties.METHOD, ASYMMETRIC_ALGORITHM);
		properties.addProperty(CryptoProperties.FILE_NAME, inputFilePath);
		properties.addProperty(CryptoProperties.DATA, encryptedText);

		fileManager.writePropertiesToFile(properties, outputFilePath);
	}

	public void decryptFileSymmetric(String inputFilePath, String outputFilePath, String keyFilePath)
			throws IOException, CryptoException {
		CryptoProperties fileProperties = fileManager.readPropertiesFromFile(inputFilePath);
		String data = fileProperties.valueAssembled(CryptoProperties.DATA);

		CryptoProperties keyProperties = fileManager.readPropertiesFromFile(keyFilePath);
		String key = keyProperties.valueAssembled(CryptoProperties.SECRET_KEY);

		byte[] decodedKey = {};
		try {
			decodedKey = Hex.decodeHex(key.toCharArray());
		} catch (DecoderException e) {
			throw new CryptoException();
		}
		SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, SYMMETRIC_ALGORITHM);

		byte[] encrypted = Base64.getDecoder().decode(data);
		byte[] decrypted = crypt(SYMMETRIC_ALGORITHM, secretKey, Cipher.DECRYPT_MODE, encrypted);

		fileManager.writeFile(outputFilePath, new String(decrypted));
	}

	public void decryptFileAsymmetric(String inputFilePath, String outputFilePath, String publicKeyFilePath)
			throws IOException, CryptoException {
		CryptoProperties fileProperties = fileManager.readPropertiesFromFile(inputFilePath);
		String data = fileProperties.valueAssembled(CryptoProperties.DATA);

		CryptoProperties keyProperties = fileManager.readPropertiesFromFile(publicKeyFilePath);
		String keyModulus = keyProperties.valueAssembled(CryptoProperties.MODULUS);
		String keyExp = keyProperties.valueAssembled(CryptoProperties.PUBLIC_EXP);
		RSAPublicKeySpec publicKey = new RSAPublicKeySpec(new BigInteger(keyModulus, 16), new BigInteger(keyExp, 16));

		PublicKey key = null;
		try {
			key = KeyFactory.getInstance(ASYMMETRIC_ALGORITHM).generatePublic(publicKey);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new CryptoException();
		}

		byte[] encrypted = Base64.getDecoder().decode(data);
		byte[] decrypted = crypt(ASYMMETRIC_ALGORITHM, key, Cipher.DECRYPT_MODE, encrypted);

		fileManager.writeFile(outputFilePath, new String(decrypted));
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

	public String calculateHash(String inputFilePath, String outputFilePath) throws IOException {
		String inputFileContent = fileManager.readFile(inputFilePath);
		String hash = DigestUtils.sha1Hex(inputFileContent);

		CryptoProperties properties = new CryptoProperties();

		properties.addProperty(CryptoProperties.DESCRIPTION, "File hash");
		properties.addProperty(CryptoProperties.METHOD, HASH_METHOD);
		properties.addProperty(CryptoProperties.FILE_NAME, inputFilePath);
		properties.addProperty(CryptoProperties.SIGNATURE, hash);

		fileManager.writePropertiesToFile(properties, outputFilePath);

		return hash;
	}

}
