package application.crypto.algorithm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class SHA1 implements MessageDigestAlgorithm {

	private static final int BLOCK_SIZE = 512;

	private static String init(byte[] data) {

		// Convert to its binary representation
		String bits = new BigInteger(data).toString(2);

		// Add missing leading zeros
		while (bits.length() % 8 != 0) {
			bits = "0" + bits;
		}

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

	private static int f(int t, int B, int C, int D) {
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

	private static int K(int t) {
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

	private static int ROTL(int number, int distance) {
		int q = (number << distance) | (number >>> (32 - distance));
		return q;
	}

	@Override
	public byte[] calculateMessageDigest(byte[] data) {
		// Fill the array until it can be divided on blocks of size BLOCK_SIZE
		String bits = init(data);

		int numOfBlocks = bits.length() / BLOCK_SIZE;

		int H0 = 0x67452301;
		int H1 = 0xEFCDAB89;
		int H2 = 0x98BADCFE;
		int H3 = 0x10325476;
		int H4 = 0xC3D2E1F0;

		for (int i = 0; i < numOfBlocks; i++) {
			int[] W = new int[80];
			int offset = i * BLOCK_SIZE;

			for (int j = 0; j < 16; j++) {
				String word = bits.substring(offset + j * 32, offset + (j + 1) * 32);
				W[j] = Integer.parseUnsignedInt(word, 2);
			}

			for (int t = 16; t < W.length; t++) {
				W[t] = ROTL(W[t - 3] ^ W[t - 8] ^ W[t - 14] ^ W[t - 16], 1);
			}

			int A = H0;
			int B = H1;
			int C = H2;
			int D = H3;
			int E = H4;

			for (int t = 0; t < W.length; t++) {
				int tmp = ROTL(A, 5) + f(t, B, C, D) + E + W[t] + K(t);
				E = D;
				D = C;
				C = ROTL(B, 30);
				B = A;
				A = tmp;
			}

			H0 = H0 + A;
			H1 = H1 + B;
			H2 = H2 + C;
			H3 = H3 + D;
			H4 = H4 + E;
		}

		byte[] H0bytes = ByteBuffer.allocate(4).putInt(H0).array();
		byte[] H1bytes = ByteBuffer.allocate(4).putInt(H1).array();
		byte[] H2bytes = ByteBuffer.allocate(4).putInt(H2).array();
		byte[] H3bytes = ByteBuffer.allocate(4).putInt(H3).array();
		byte[] H4bytes = ByteBuffer.allocate(4).putInt(H4).array();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			outputStream.write(H0bytes);
			outputStream.write(H1bytes);
			outputStream.write(H2bytes);
			outputStream.write(H3bytes);
			outputStream.write(H4bytes);
		} catch (IOException e) {
		}

		return outputStream.toByteArray();
	}

}
