package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EWalletTransactionsDTOS {
	private int id;
	private double amount;
	private String transactionType;
	private String orderId;
	private String status;
	private int relatedWalletId;
	private String description;
	private LocalDateTime createdAt;
	private String eWallets;
}
