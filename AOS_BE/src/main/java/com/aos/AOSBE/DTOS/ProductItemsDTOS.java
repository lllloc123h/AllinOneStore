package com.aos.AOSBE.DTOS;
import com.aos.AOSBE.Entity.BaseProducts;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemsDTOS {
	private int id;
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
