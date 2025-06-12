package com.aos.AOSBE.DTOS;

import lombok.Data;

@Data
public class UpdateProfileDTO {
    private String name;
    private String email;
    private String phone;
    private String avarta;
    private String address;
}
