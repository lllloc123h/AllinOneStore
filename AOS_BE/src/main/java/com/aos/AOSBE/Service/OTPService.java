package com.aos.AOSBE.Service;

import org.springframework.stereotype.Service;

import com.aos.AOSBE.DTOS.OtpSessionData;
import com.aos.AOSBE.DTOS.RegisterRequestDTO;
import com.aos.AOSBE.DTOS.VerifyOtpDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OTPService {
    private final OtpSessionData otpSessionData;

    // 🔐 Đăng ký tài khoản
    public int generateOtpToRegister(Long time, RegisterRequestDTO registerRequestDTO) {
        return otpSessionData.generateOtpToRegister(time, registerRequestDTO);
    }

    public boolean checkOtpToRegister(int code) throws Exception {
        return otpSessionData.checkOtp(OtpSessionData.VERIFY_EMAIL_TO_REGISTER, code);
    }

    public RegisterRequestDTO getRegisterDTO() {
        return otpSessionData.getRegisterRequestDTO(OtpSessionData.VERIFY_EMAIL_TO_REGISTER);
    }

    // 🆕 Quên mật khẩu
    public int generateOtpToResetPassword(Long time, String email) {
        return otpSessionData.generateOtpForForgotPassword(time, email);
    }

    public boolean checkOtpToResetPassword(String email, String code) {
        VerifyOtpDTO storedOtp = OtpStore.getOtpDtoByEmail(email);

        if (storedOtp == null) {
            return false;
        }

        return storedOtp.getOtpCode().equals(code);
    }

    public String getEmailToResetPassword() {
        return otpSessionData.getEmailFromOtp(OtpSessionData.FORGOT_PASSWORD);
    }
}