package com.aos.AOSBE.Service;

import com.aos.AOSBE.DTOS.VerifyOtpDTO;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OtpStore {
    private static final Map<String, VerifyOtpDTO> otpData = new HashMap<>();
    private static final Map<String, String> tokenMap = new HashMap<>();

    private static String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    // ✅ Thêm hàm putOtp để lưu VerifyOtpDTO
    public static void putOtp(String email, VerifyOtpDTO dto) {
        String normalizedEmail = normalizeEmail(email);
        otpData.put(normalizedEmail, dto);
    }

    public static void saveOtp(String email, String otp) {
        String normalizedEmail = normalizeEmail(email);
        VerifyOtpDTO dto = new VerifyOtpDTO(normalizedEmail, otp, LocalDateTime.now());
        otpData.put(normalizedEmail, dto);
    }

    public static VerifyOtpDTO getOtpDtoByEmail(String email) {
        String normalizedEmail = normalizeEmail(email);
        return otpData.get(normalizedEmail);
    }

    public static void clearOtp(String email) {
        String normalizedEmail = normalizeEmail(email);
        otpData.remove(normalizedEmail);
    }

    public static boolean hasOtp(String email) {
        String normalizedEmail = normalizeEmail(email);
        return otpData.containsKey(normalizedEmail);
    }

    public static String getOtpByEmail(String email) {
        VerifyOtpDTO dto = getOtpDtoByEmail(email);
        return (dto != null) ? dto.getOtpCode() : "0";
    }

    public static void saveToken(String email, String token) {
        String normalizedEmail = normalizeEmail(email);
        tokenMap.put(normalizedEmail, token);
    }

    public static String getTokenByEmail(String email) {
        String normalizedEmail = normalizeEmail(email);
        return tokenMap.getOrDefault(normalizedEmail, "");
    }

    public static void clearToken(String email) {
        String normalizedEmail = normalizeEmail(email);
        tokenMap.remove(normalizedEmail);
    }
}