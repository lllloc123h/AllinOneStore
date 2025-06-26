package com.aos.AOSBE.DTOS;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TopUpRequestDTO {
	private Long userId;
	private BigDecimal amount;
}
