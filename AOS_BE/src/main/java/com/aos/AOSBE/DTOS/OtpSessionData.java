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
        private RegisterRequestDTO registerRequestDTO;
        public OtpInfo() {
        }

        public OtpInfo(int code, long expireAt, RegisterRequestDTO registerRequestDTO) {
            this.code = code;
            this.expireAt = expireAt;
            this.registerRequestDTO = registerRequestDTO;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public long getExpireAt() {
            return expireAt;
        }

        public void setExpireAt(long expireAt) {
            this.expireAt = expireAt;
        }

        public RegisterRequestDTO getRegisterRequestDTO() {
            return registerRequestDTO;
        }

        public void setRegisterRequestDTO(RegisterRequestDTO registerRequestDTO) {
            this.registerRequestDTO = registerRequestDTO;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expireAt;
        }
    }
}


