package com.aos.AOSBE.CommonFunctions;

import java.util.Random;

public class CommonFunctions {
	public String generateVerificationCode() {
		Random random = new Random();
		int code = 100000 + random.nextInt(900000); // ensures 6 digits
		return String.valueOf(code);
	}
}
