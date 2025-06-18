package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsDTOS {
	private int id;
	private int qty;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String accounts;
	private int productItems;
}
