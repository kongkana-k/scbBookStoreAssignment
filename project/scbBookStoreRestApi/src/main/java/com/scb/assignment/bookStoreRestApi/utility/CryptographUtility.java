package com.scb.assignment.bookStoreRestApi.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;

public class CryptographUtility {

	public static final int TOKEN_TIME_IN_MINUTE = 10;

	public static final String APP_SECRET = "BookStoreSecret";

	public static String shaToBase64(String originalString, int salt) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] result = digest
				.digest((APP_SECRET + originalString + Integer.toString(salt)).getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(result);
	}

	public static int randomInt() {
		return (int) (Math.random() * Integer.MAX_VALUE);
	}

	public static Timestamp getNextExpiredTime() {
		return Timestamp.valueOf(LocalDateTime.now().plusMinutes(TOKEN_TIME_IN_MINUTE));
	}
}
