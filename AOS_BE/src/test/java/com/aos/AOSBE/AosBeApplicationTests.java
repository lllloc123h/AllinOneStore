package com.aos.AOSBE;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AosBeApplicationTests {
//	@BeforeAll
//	static void loadEnv() {
//		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
//
//		System.setProperty("DB_URL", dotenv.get("DB_URL"));
//		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
//		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
//		System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
//		System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
//		System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
//		System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
//		System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
//	}

	@Test
	void contextLoads() {
	}

}
