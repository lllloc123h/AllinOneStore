package com.aos.AOSBE.CommonFunctions;

import java.security.SecureRandom;

public class CommonFunctions {

	private static final SecureRandom secureRandom = new SecureRandom();

	public String generateVerificationCode() {
		int code = 100000 + secureRandom.nextInt(900000); // ensures 6 digits
		return String.valueOf(code);
	}
}
