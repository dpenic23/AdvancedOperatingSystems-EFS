package application.crypto.algorithm;

import java.math.BigInteger;

public class SHA1 implements MessageDigestAlgorithm {

	private static final int BLOCK_SIZE = 20;

	private static String init(byte[] data) {
		// Convert to its binary representation
		String bits = new BigInteger(data).toString(2);

		// Size of the data, number of bits in base 2
		String size2 = Integer.toBinaryString(bits.length());

		int lastBlockSize = bits.length() % BLOCK_SIZE;
		int newBlockSize = lastBlockSize + 1 + size2.length();
		int numOfZeros = 0;

		if (newBlockSize <= BLOCK_SIZE) {
			numOfZeros = BLOCK_SIZE - newBlockSize;
		} else {
			numOfZeros = (BLOCK_SIZE - 1 - lastBlockSize) + (BLOCK_SIZE - size2.length());
		}

		StringBuilder sb = new StringBuilder(bits);
		sb.append("1");

		for (int i = 0; i < numOfZeros; i++) {
			sb.append("0");
		}

		sb.append(size2);

		return sb.toString();
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
		// Fill the array until it can be divided on blocks of size BLOCK_SIZE
		String bits = init(data);

		int numOfBlocks = bits.length() / BLOCK_SIZE;

		for (int i = 0; i < numOfBlocks; i++) {

		}

		long H0 = 0x67452301;
		long H1 = 0xEFCDAB89;
		long H2 = 0x98BADCFE;
		long H3 = 0x10325476;
		long H4 = 0xC3D2E1F0;

		return null;
	}

	public static void main(String[] args) {
		String a = "josko";

		String b = init(a.getBytes());

		System.out.println(new BigInteger(a.getBytes()).toString(2));
		System.out.print(b);
	}

}
