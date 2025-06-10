package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;

import com.aos.AOSBE.Entity.BaseProducts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemsDTOS {
	private int id;
	private String name;
	private double cost;
	private double price;
	private boolean isPromote;
	private int turnBuy;
	private String description;
	private String sku;
	private int safetyStock;
	private int qty;
	private LocalDateTime sellStart;
	private LocalDateTime sellEnd;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private BaseProducts baseProducts;
}
