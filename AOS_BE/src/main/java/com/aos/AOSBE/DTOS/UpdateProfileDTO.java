package com.aos.AOSBE.DTOS;

import lombok.Data;

@Data
public class UpdateProfileDTO {
	private String fullname;
	private String email;
	private String phone;
	private String avatarUrl;
	private String address;

	private Integer provinceName;
	private Integer districtName;
	private String wardName;

	private String province;
	private String district;
	private String ward;
	private String street;
}
