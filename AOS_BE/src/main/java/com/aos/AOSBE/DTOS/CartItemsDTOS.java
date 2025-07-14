package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;
import java.util.UUID;

import com.aos.AOSBE.Entity.Promotions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsDTOS {
	private int id;
	private int qty;
	private String name;
	private String mainImageUrl;
	private Double price;
	private String sku;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String accounts;
	private int productItems;
	private Promotions promotions;
	private String comboGroup;
	private Integer comboQty;
	private UUID comboGroupId;
	private Boolean isGift;
}
