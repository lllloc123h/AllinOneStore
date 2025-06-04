package com.aos.AOSBE.DTOS;

import java.time.LocalDate;

import com.aos.AOSBE.Entity.Accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class loginRequestDTOS {
	String email;
	String password;
}
