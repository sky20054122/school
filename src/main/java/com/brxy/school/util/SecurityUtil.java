package com.brxy.school.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityUtil {
	private static final String SECURITY_ALGORITHM = "DES";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SecurityUtil.class);

	private SecurityUtil() {
		super();
	}

	public static final String decrypt(String data, String key) {
		try {
			return new String(
					decrypt(hex2byte(data.getBytes()), key.getBytes()));
		} catch (Exception e) {
			LOGGER.error("decrypt error", e);
		}
		return null;
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("The length is not exception");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	private static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(SECURITY_ALGORITHM);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(SECURITY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	public static final String encrypt(String data, String key) {
		try {
			if (key.length() < 8) {
				StringBuilder newKey = new StringBuilder(key);
				for (int index = key.length(); index < 9; index++) {
					newKey.append("" + (index + 1));
				}
				return byte2hex(encrypt(data.getBytes(), newKey.toString()
						.getBytes()));
			}
			return byte2hex(encrypt(data.getBytes(), key.getBytes()));
		} catch (Exception e) {
			LOGGER.error("encrypt error", e);
		}
		return null;
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	private static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(SECURITY_ALGORITHM);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(SECURITY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

}
