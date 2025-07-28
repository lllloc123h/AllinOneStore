package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@SessionScope
public class OtpSessionData {
private Map<String, OtpInfo> mapOtp = new HashMap<>();
public final static String VERIFY_EMAIL_TO_REGISTER = "verifyEmailToRegister";

    public int generateOtpToRegister(Long time, RegisterRequestDTO registerRequestDTO) {
        if (mapOtp.containsKey(VERIFY_EMAIL_TO_REGISTER)) {
            mapOtp.remove(VERIFY_EMAIL_TO_REGISTER);
        }
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        OtpInfo otpInfo = new OtpInfo(code, System.currentTimeMillis() + time, registerRequestDTO);
        System.err.println("OTP hết hạn lúc : "+new SimpleDateFormat("HH:mm:ss").format(otpInfo.getExpireAt()));
        mapOtp.put(VERIFY_EMAIL_TO_REGISTER, otpInfo);
        return mapOtp.get(VERIFY_EMAIL_TO_REGISTER).getCode();
    }
    public RegisterRequestDTO getRegisterRequestDTO(String key) {
        if (mapOtp.containsKey(key)) {
            return mapOtp.get(key).getRegisterRequestDTO();
        }
        return null;
    }
    public boolean checkOtp(String key, int code) {
        if (mapOtp.containsKey(key)) {
            if (mapOtp.get(key).isExpired()) {
                System.err.println("OTP hết hạn");
                return false;
            }
            return mapOtp.get(key).getCode() == code && !mapOtp.get(key).isExpired();
        }
        return false;
    }




    private static class OtpInfo {
        private int code;
        private long expireAt;
        private RegisterRequestDTO registerRequestDTO; // dùng cho đăng ký
        private String email; // dùng cho quên mật khẩu

        public OtpInfo() {}

        public OtpInfo(int code, long expireAt, RegisterRequestDTO registerRequestDTO) {
            this.code = code;
            this.expireAt = expireAt;
            this.registerRequestDTO = registerRequestDTO;
        }

        public OtpInfo(int code, long expireAt, String email) {
            this.code = code;
            this.expireAt = expireAt;
            this.email = email;
        }

        public int getCode() { return code; }

        public long getExpireAt() { return expireAt; }

        public RegisterRequestDTO getRegisterRequestDTO() { return registerRequestDTO; }

        public String getEmail() { return email; }

        public boolean isExpired() {
            return System.currentTimeMillis() > expireAt;
        }
    }
    public final static String FORGOT_PASSWORD = "forgotPassword";

    public int generateOtpForForgotPassword(Long time, String email) {
        mapOtp.remove(FORGOT_PASSWORD);
        int code = new Random().nextInt(900000) + 100000;
        OtpInfo otpInfo = new OtpInfo(code, System.currentTimeMillis() + time, email);
        System.err.println("OTP quên mật khẩu hết hạn lúc : " +
            new SimpleDateFormat("HH:mm:ss").format(otpInfo.getExpireAt()));
        mapOtp.put(FORGOT_PASSWORD, otpInfo);
        return code;
    }

    public String getEmailFromOtp(String key) {
        if (mapOtp.containsKey(key)) {
            return mapOtp.get(key).getEmail();
        }
        return null;
    }
	public boolean checkOtpForgetPassword(String forgotPassword, String email, int code) {
		// TODO Auto-generated method stub
		return false;
	}
}


