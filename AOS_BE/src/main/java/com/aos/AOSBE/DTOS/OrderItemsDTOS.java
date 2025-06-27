package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDTOS {
	private int id;
	private int promotionId;
	private int qty;
	private double cost;
	private boolean isGift;
	private double sellingPrice;
	private double total;
	private String couponCode;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int orders;
	private int productItems;
	private int promotions;
}
