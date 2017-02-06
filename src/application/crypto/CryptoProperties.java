package application.crypto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CryptoProperties {

	public static final String DESCRIPTION = "Description";
	public static final String SECRET_KEY = "Secret key";
	public static final String FILE_NAME = "File name";
	public static final String METHOD = "Method";
	public static final String KEY_LENGTH = "Key length";
	public static final String INIT_VECTOR = "Initialization vector";
	public static final String MODULUS = "Modulus";
	public static final String PUBLIC_EXP = "Public exponent";
	public static final String PRIVATE_EXP = "Private exponent";
	public static final String SIGNATURE = "Signature";
	public static final String DATA = "Data";
	public static final String ENVELOPE_DATA = "Envelope data";
	public static final String ENVELOPE_CRYPT_KEY = "Envelope crypt key";

	private static final int LENGTH_LIMIT = 60;

	private Map<String, List<String>> properties = new LinkedHashMap<>();

	public CryptoProperties() {
	}

	public Set<String> keySet() {
		return properties.keySet();
	}

	public List<String> value(String key) {
		return properties.get(key);
	}

	public String valueAssembled(String key) {
		StringBuilder sb = new StringBuilder();

		for (String line : properties.get(key)) {
			sb.append(line);
		}

		return sb.toString();
	}

	public void addProperty(String key, List<String> value) {
		if (!properties.containsKey(key)) {
			properties.put(key, new ArrayList<>());
		}

		properties.get(key).addAll(value);
	}

	public void addProperty(String key, String value) {
		if (!properties.containsKey(key)) {
			properties.put(key, new ArrayList<>());
		}

		while (value.length() > LENGTH_LIMIT) {
			properties.get(key).add(value.substring(0, LENGTH_LIMIT));
			value = value.substring(LENGTH_LIMIT, value.length());
		}

		properties.get(key).add(value);
	}

}
