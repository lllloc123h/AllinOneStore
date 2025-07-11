package com.aos.AOSBE.DTOS;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MOMOPAYRequestDTO {
	private int OrderId;
	private BigDecimal finalToTal;
}
