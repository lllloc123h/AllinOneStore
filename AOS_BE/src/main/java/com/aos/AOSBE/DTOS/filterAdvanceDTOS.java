package com.aos.AOSBE.DTOS;

import lombok.Data;

@Data
public class filterAdvanceDTOS {
	private int id;
	private int productItemId;
	private String name;
	private String material;
	private String descriptionOfSku;

//	handleListSkuToFilter.getDescriptionOfSku(entity.getProductItems().getSku()),
	private int categoryId;
	private String mainImage;

	private boolean isCustom;
	private int turnBuy;
	private String sku;
	private double rating;
	private boolean isActive;
	private int qty;
	private double price;
	private int safetyStock;
}
