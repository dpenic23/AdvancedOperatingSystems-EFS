package application.crypto.algorithm;

public class SHA1 implements MessageDigestAlgorithm {

	private static void init(byte[] data) {
		int numOfBits = data.length * 8;
	}

	private static long f(long t, long B, long C, long D) {
		if (t >= 0 && t <= 19) {
			return ((B & C) | ((~B) & D));
		} else if (t >= 20 && t <= 39) {
			return (B ^ C ^ D);
		} else if (t >= 40 && t <= 59) {
			return ((B & C) | (B & D) | (C & D));
		} else {
			return (B ^ C ^ D);
		}
	}

	private static long K(long t) {
		if (t >= 0 && t <= 19) {
			return 0x5A827999;
		} else if (t >= 20 && t <= 39) {
			return 0x6ED9EBA1;
		} else if (t >= 40 && t <= 59) {
			return 0x8F1BBCDC;
		} else {
			return 0xCA62C1D6;
		}
	}

	@Override
	public byte[] calculateMessageDigest(byte[] data) {
		// Copy the data so it's not changed
		byte[] dataCopy = new byte[data.length];
		System.arraycopy(data, 0, dataCopy, 0, data.length);

		// Fill the array until it can be divided on blocks of size 512
		init(dataCopy);

		int numOfBlocks = dataCopy.length * 8 / 512;

		long H0 = 0x67452301;
		long H1 = 0xEFCDAB89;
		long H2 = 0x98BADCFE;
		long H3 = 0x10325476;
		long H4 = 0xC3D2E1F0;

		return null;
	}

}
