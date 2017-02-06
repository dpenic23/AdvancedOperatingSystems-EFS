package application.crypto;

public enum Properties {

	DESCRIPTION("Description"), 
	SECRET_KEY("Secret key"), 
	FILE_NAME("File name"), 
	METHOD("Method"), 
	KEY_LENGTH("Key length"), 
	INIT_VECTOR("Initialization vector"), 
	MODULUS("Modulus"), 
	PUBLIC_EXP("Public exponent"), 
	PRIVATE_EXP("Private exponent"), 
	SIGNATURE("Signature"), 
	DATA("Data"), 
	ENVELOPE_DATA("Envelope data"), 
	ENVELOPE_CRYPT_KEY("Envelope crypt key");

	private final String text;

	private Properties(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
