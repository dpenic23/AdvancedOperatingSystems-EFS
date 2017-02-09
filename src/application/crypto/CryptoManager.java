package application.crypto;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Hex;

import application.file.FileManager;

public class CryptoManager {

	private static final String SYMMETRIC_ALGORITHM = "AES";
	private static final String ASYMMETRIC_ALGORITHM = "RSA";
	private static final String HASH_METHOD = "SHA-1";

	private FileManager fileManager = new FileManager();

	public CryptoManager() {
	}

	public void generateSymmetricKey(String keyFilePath, int keySize) throws IOException, CryptoException {
		// Generate a symmetric key
		SecretKey key = CryptoMethod.generateKey(SYMMETRIC_ALGORITHM, keySize);
		String keyEncoded = Hex.encodeHexString(key.getEncoded());

		// Define its properties and write it to the specified file
		CryptoProperties keyProperties = new CryptoProperties();

		keyProperties.addProperty(CryptoProperties.DESCRIPTION, "Secret key");
		keyProperties.addProperty(CryptoProperties.METHOD, SYMMETRIC_ALGORITHM);
		keyProperties.addProperty(CryptoProperties.KEY_LENGTH, Integer.toHexString(keySize));
		keyProperties.addProperty(CryptoProperties.SECRET_KEY, keyEncoded);

		fileManager.writePropertiesToFile(keyProperties, keyFilePath);
	}

	public void encryptFileSymmetric(String inputFilePath, String outputFilePath, String keyFilePath)
			throws IOException, CryptoException {
		// Read all the data from the file to encrypt
		String data = fileManager.readFile(inputFilePath);

		// Read the secret key
		CryptoProperties keyProperties = fileManager.readPropertiesFromFile(keyFilePath);
		String key = keyProperties.valueAssembled(CryptoProperties.SECRET_KEY);
		SecretKey secretKey = CryptoMethod.getSecretKey(SYMMETRIC_ALGORITHM, key);

		// Encrypt the data
		byte[] encrypted = CryptoMethod.crypt(SYMMETRIC_ALGORITHM, secretKey, Cipher.ENCRYPT_MODE, data.getBytes());
		String encryptedData = Base64.getEncoder().encodeToString(encrypted);

		// Define the file properties and write it to the specified file
		CryptoProperties properties = new CryptoProperties();

		properties.addProperty(CryptoProperties.DESCRIPTION, "Crypted file");
		properties.addProperty(CryptoProperties.METHOD, SYMMETRIC_ALGORITHM);
		properties.addProperty(CryptoProperties.FILE_NAME, inputFilePath);
		properties.addProperty(CryptoProperties.DATA, encryptedData);

		fileManager.writePropertiesToFile(properties, outputFilePath);
	}

	public void decryptFileSymmetric(String inputFilePath, String outputFilePath, String keyFilePath)
			throws IOException, CryptoException {
		// Read the encrypted data
		CryptoProperties fileProperties = fileManager.readPropertiesFromFile(inputFilePath);
		String data = fileProperties.valueAssembled(CryptoProperties.DATA);

		// Read the secret key
		CryptoProperties keyProperties = fileManager.readPropertiesFromFile(keyFilePath);
		String key = keyProperties.valueAssembled(CryptoProperties.SECRET_KEY);
		SecretKey secretKey = CryptoMethod.getSecretKey(SYMMETRIC_ALGORITHM, key);

		// Decrypt the data and write the content to the file
		byte[] encrypted = Base64.getDecoder().decode(data);
		byte[] decrypted = CryptoMethod.crypt(SYMMETRIC_ALGORITHM, secretKey, Cipher.DECRYPT_MODE, encrypted);

		fileManager.writeFile(outputFilePath, new String(decrypted));
	}

	public void generateAsymmetricKeys(String publicKeyFilePath, String privateKeyFilePath, int keySize)
			throws IOException, CryptoException {
		// Generate the key pair, public and private keys
		KeyPair keyPair = CryptoMethod.generateKeyPair(ASYMMETRIC_ALGORITHM, keySize);
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		// Define the public key properties
		CryptoProperties publicKeyProperties = new CryptoProperties();

		publicKeyProperties.addProperty(CryptoProperties.DESCRIPTION, "Public key");
		publicKeyProperties.addProperty(CryptoProperties.METHOD, ASYMMETRIC_ALGORITHM);
		publicKeyProperties.addProperty(CryptoProperties.KEY_LENGTH,
				Integer.toHexString(publicKey.getModulus().bitLength()));
		publicKeyProperties.addProperty(CryptoProperties.MODULUS, publicKey.getModulus().toString(16));
		publicKeyProperties.addProperty(CryptoProperties.PUBLIC_EXP, publicKey.getPublicExponent().toString(16));

		// Define the private key properties
		CryptoProperties privateKeyProperties = new CryptoProperties();

		privateKeyProperties.addProperty(CryptoProperties.DESCRIPTION, "Private key");
		privateKeyProperties.addProperty(CryptoProperties.METHOD, ASYMMETRIC_ALGORITHM);
		privateKeyProperties.addProperty(CryptoProperties.KEY_LENGTH,
				Integer.toHexString(privateKey.getModulus().bitLength()));
		privateKeyProperties.addProperty(CryptoProperties.MODULUS, privateKey.getModulus().toString(16));
		privateKeyProperties.addProperty(CryptoProperties.PRIVATE_EXP, privateKey.getPrivateExponent().toString(16));

		// Write the defined properties to the file
		fileManager.writePropertiesToFile(publicKeyProperties, publicKeyFilePath);
		fileManager.writePropertiesToFile(privateKeyProperties, privateKeyFilePath);

	}

	public void encryptFileAsymmetric(String inputFilePath, String outputFilePath, String keyFilePath)
			throws IOException, CryptoException {
		// Read all the data to be encrypted
		String data = fileManager.readFile(inputFilePath);

		// Read the public key from the file
		CryptoProperties keyProperties = fileManager.readPropertiesFromFile(keyFilePath);
		String modulus = keyProperties.valueAssembled(CryptoProperties.MODULUS);
		String exp = keyProperties.valueAssembled(CryptoProperties.PUBLIC_EXP);
		PublicKey key = CryptoMethod.getPublicKey(ASYMMETRIC_ALGORITHM, modulus, exp);

		// Encrypt the data
		byte[] encrypted = CryptoMethod.crypt(ASYMMETRIC_ALGORITHM, key, Cipher.ENCRYPT_MODE, data.getBytes());
		String encryptedData = Base64.getEncoder().encodeToString(encrypted);

		// Define the file properties and write it to the specified file
		CryptoProperties properties = new CryptoProperties();

		properties.addProperty(CryptoProperties.DESCRIPTION, "Crypted file");
		properties.addProperty(CryptoProperties.METHOD, ASYMMETRIC_ALGORITHM);
		properties.addProperty(CryptoProperties.FILE_NAME, inputFilePath);
		properties.addProperty(CryptoProperties.DATA, encryptedData);

		fileManager.writePropertiesToFile(properties, outputFilePath);
	}

	public void decryptFileAsymmetric(String inputFilePath, String outputFilePath, String keyFilePath)
			throws IOException, CryptoException {
		// Read the encrypted data
		CryptoProperties fileProperties = fileManager.readPropertiesFromFile(inputFilePath);
		String data = fileProperties.valueAssembled(CryptoProperties.DATA);

		// Read the private key from the file
		CryptoProperties keyProperties = fileManager.readPropertiesFromFile(keyFilePath);
		String modulus = keyProperties.valueAssembled(CryptoProperties.MODULUS);
		String exp = keyProperties.valueAssembled(CryptoProperties.PRIVATE_EXP);
		PrivateKey key = CryptoMethod.getPrivateKey(ASYMMETRIC_ALGORITHM, modulus, exp);

		// Decrypt the data
		byte[] encrypted = Base64.getDecoder().decode(data);
		byte[] decrypted = CryptoMethod.crypt(ASYMMETRIC_ALGORITHM, key, Cipher.DECRYPT_MODE, encrypted);

		// Write the data to the file
		fileManager.writeFile(outputFilePath, new String(decrypted));
	}

	public String calculateHash(String inputFilePath, String outputFilePath) throws IOException {
		// Read the data and calculate the hash value
		String data = fileManager.readFile(inputFilePath);
		String hash = CryptoMethod.calculateHash(data);

		// Define the properties and write it to the file
		CryptoProperties properties = new CryptoProperties();

		properties.addProperty(CryptoProperties.DESCRIPTION, "File hash");
		properties.addProperty(CryptoProperties.METHOD, HASH_METHOD);
		properties.addProperty(CryptoProperties.FILE_NAME, inputFilePath);
		properties.addProperty(CryptoProperties.SIGNATURE, hash);

		fileManager.writePropertiesToFile(properties, outputFilePath);

		return hash;
	}

}
