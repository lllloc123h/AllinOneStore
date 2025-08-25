package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequestDTOS {
	private String requestType;
	private Long amount; // amount to withdraw
	private String bankCode; // bank code (e.g. "VCB", "MB", "TCB")
	private String bankAccount;
	private String bankAccountHolderName;
	private String walletId;

}
