package com.aos.AOSBE.DTOS;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountProfileDTO {
    private String fullname;
    private String email;
    private String phone;
}
