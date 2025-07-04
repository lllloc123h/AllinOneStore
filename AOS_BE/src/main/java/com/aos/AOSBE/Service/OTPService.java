package com.aos.AOSBE.Service;

import org.springframework.stereotype.Service;

import com.aos.AOSBE.DTOS.OtpSessionData;
import com.aos.AOSBE.DTOS.RegisterRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OTPService {
	private final OtpSessionData otpSessionData;

	public int generateOtpToRegister(Long time, RegisterRequestDTO registerRequestDTO) {
		return otpSessionData.generateOtpToRegister(time, registerRequestDTO);
	}

	public boolean checkOtpToRegister(int code) throws Exception {
		return otpSessionData.checkOtp(OtpSessionData.VERIFY_EMAIL_TO_REGISTER, code);
	}

	public RegisterRequestDTO getRegisterDTO() {
		return otpSessionData.getRegisterRequestDTO(OtpSessionData.VERIFY_EMAIL_TO_REGISTER);
	}

}
