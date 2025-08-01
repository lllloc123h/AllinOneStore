package com.aos.AOSBE.DTOS;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateProfileDTO {
	private String fullname;
	private String phone;
	private String avatarUrl;
	private boolean gender;
	private LocalDateTime birthday;
}
