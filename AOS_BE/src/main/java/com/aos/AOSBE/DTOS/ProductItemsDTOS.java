package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;
import java.util.List;

import com.aos.AOSBE.Entity.BaseProducts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemsDTOS {
	private int id;
	private int productItemsId;
	private double cost;
	private double price;
	private int turnBuy;
	private String description;
	private String sku;
	private int safetyStock;
	private int qty;
	private LocalDateTime sellStart;
	private LocalDateTime sellEnd;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int baseId;
	private String name;
	private String material;
	private String imageUrl;
	private BaseProducts baseProducts;
	private Boolean isGift;
	private List<PromotionsDTOS> promotions;
	private List<ProductImagesDTOS> images;
	private boolean isActive;

	public ProductItemsDTOS(int id, double price, String description, String sku, int qty, Boolean isGift) {
		this.id = id;
		this.price = price;
		this.description = description;
		this.sku = sku;
		this.qty = qty;
		this.isGift = isGift;
	}

	public ProductItemsDTOS(double cost, int id, double price, int turnBuy, String description, int safetyStock,
			String sku, int qty, LocalDateTime sellStart, LocalDateTime sellEnd, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.cost = cost;
		this.id = id;
		this.price = price;
		this.turnBuy = turnBuy;
		this.description = description;
		this.safetyStock = safetyStock;
		this.sku = sku;
		this.qty = qty;
		this.sellStart = sellStart;
		this.sellEnd = sellEnd;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public ProductItemsDTOS(int id, double cost, double price, int turnBuy, String description, String sku,
			int safetyStock, int qty, LocalDateTime sellStart, LocalDateTime sellEnd, LocalDateTime createdAt,
			LocalDateTime updatedAt, int baseId, String name, String material, String imageUrl) {
		super();
		this.id = id;
		this.cost = cost;
		this.price = price;
		this.turnBuy = turnBuy;
		this.description = description;
		this.sku = sku;
		this.safetyStock = safetyStock;
		this.qty = qty;
		this.sellStart = sellStart;
		this.sellEnd = sellEnd;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.baseId = baseId;
		this.name = name;
		this.material = material;
		this.imageUrl = imageUrl;
	}

	public ProductItemsDTOS(int id, double cost, double price, int turnBuy, String description, String sku,
			int safetyStock, int qty, LocalDateTime sellStart, LocalDateTime sellEnd, LocalDateTime createdAt,
			LocalDateTime updatedAt, int baseId, String name, String material, String imageUrl,
			BaseProducts baseProducts, List<PromotionsDTOS> promotions, List<ProductImagesDTOS> images) {
		super();
		this.id = id;
		this.cost = cost;
		this.price = price;
		this.turnBuy = turnBuy;
		this.description = description;
		this.sku = sku;
		this.safetyStock = safetyStock;
		this.qty = qty;
		this.sellStart = sellStart;
		this.sellEnd = sellEnd;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.baseId = baseId;
		this.name = name;
		this.material = material;
		this.imageUrl = imageUrl;
		this.baseProducts = baseProducts;
		this.promotions = promotions;
		this.images = images;
	}

	public ProductItemsDTOS(int id, double cost, double price, int turnBuy, String description, String sku,
			int safetyStock, int qty, LocalDateTime sellStart, LocalDateTime sellEnd, LocalDateTime createdAt,
			LocalDateTime updatedAt, int baseId, String name, String material, String imageUrl,
			BaseProducts baseProducts) {
		super();
		this.id = id;
		this.cost = cost;
		this.price = price;
		this.turnBuy = turnBuy;
		this.description = description;
		this.sku = sku;
		this.safetyStock = safetyStock;
		this.qty = qty;
		this.sellStart = sellStart;
		this.sellEnd = sellEnd;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.baseId = baseId;
		this.name = name;
		this.material = material;
		this.imageUrl = imageUrl;
		this.baseProducts = baseProducts;
	}

	public ProductItemsDTOS(int id, double cost, double price, int turnBuy, String description, String sku,
			int safetyStock, int qty, LocalDateTime sellStart, LocalDateTime sellEnd, LocalDateTime createdAt,
			LocalDateTime updatedAt, int baseId, String name, String material, String imageUrl,
			BaseProducts baseProducts, List<ProductImagesDTOS> images) {
		super();
		this.id = id;
		this.cost = cost;
		this.price = price;
		this.turnBuy = turnBuy;
		this.description = description;
		this.sku = sku;
		this.safetyStock = safetyStock;
		this.qty = qty;
		this.sellStart = sellStart;
		this.sellEnd = sellEnd;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.baseId = baseId;
		this.name = name;
		this.material = material;
		this.imageUrl = imageUrl;
		this.baseProducts = baseProducts;
		this.images = images;
	}

	public ProductItemsDTOS(int id, double cost, double price, int turnBuy, String description, String sku,
			int safetyStock, int qty, LocalDateTime sellStart, LocalDateTime sellEnd, LocalDateTime createdAt,
			LocalDateTime updatedAt, int baseId, String name, String material, String imageUrl,
			BaseProducts baseProducts, List<ProductImagesDTOS> images, boolean isActive) {
		super();
		this.id = id;
		this.cost = cost;
		this.price = price;
		this.turnBuy = turnBuy;
		this.description = description;
		this.sku = sku;
		this.safetyStock = safetyStock;
		this.qty = qty;
		this.sellStart = sellStart;
		this.sellEnd = sellEnd;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.baseId = baseId;
		this.name = name;
		this.material = material;
		this.imageUrl = imageUrl;
		this.baseProducts = baseProducts;
		this.images = images;
		this.isActive = isActive;
	}

	public ProductItemsDTOS(int id, double cost, double price, int turnBuy, String description, String sku,
			int safetyStock, int qty, LocalDateTime sellStart, LocalDateTime sellEnd, LocalDateTime createdAt,
			LocalDateTime updatedAt, int baseId) {
		super();
		this.id = id;
		this.cost = cost;
		this.price = price;
		this.turnBuy = turnBuy;
		this.description = description;
		this.sku = sku;
		this.safetyStock = safetyStock;
		this.qty = qty;
		this.sellStart = sellStart;
		this.sellEnd = sellEnd;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.baseId = baseId;
	}
}
