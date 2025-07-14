package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EWalletsDTOS {
	private String id;
	private double balance;
	private String walletType;
	private boolean isActive;
	private String codeActivce;
	private LocalDateTime createdAt;
	private String accounts;
}
