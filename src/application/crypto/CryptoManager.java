package application.crypto;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
import application.file.Pair;

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

		List<Pair> properties = new ArrayList<>();

		properties.add(new Pair(Properties.DESCRIPTION, "Secret Key"));
		properties.add(new Pair(Properties.METHOD, SYMMETRIC_ALGORITHM));
		properties.add(new Pair(Properties.SECRET_KEY, key));

		fileManager.writePropertiesToFile(properties, keyFilePath);
	}

	public void encryptFileSymmetric(String inputFilePath, String outputFilePath, String keyFilePath)
			throws IOException {
		String inputFileContent = fileManager.readFile(inputFilePath);
		String key = "";

		List<Pair> keyProperties = fileManager.readPropertiesFromFile(keyFilePath);
		for (Pair pair : keyProperties) {
			if (pair.getKey().equals(Properties.SECRET_KEY)) {
				key = pair.getValues().get(0);
				break;
			}
		}

		byte[] decoded = {};
		try {
			decoded = Hex.decodeHex(key.toCharArray());
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SecretKey secretKey = new SecretKeySpec(decoded, 0, decoded.length, "AES");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void decryptFileSymmetric(String inputFilePath, String outputFilePath, String keyFilePath)
			throws IOException {

	}

}
