package application.crypto.algorithm;

public interface MessageDigestAlgorithm {

	public byte[] calculateMessageDigest(byte[] data);

}
