package com.aos.AOSBE.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id")
	private int id;

	@Column(name = "qty")
	private int qty;

	@Column(name = "cost")
	private double cost;

	@Column(name = "is_gift")
	private boolean isGift;

	@Column(name = "selling_price")
	private double sellingPrice;

	@Column(name = "total")
	private double total;

	@Column(name = "coupon_code")
	private int couponCode;
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders orders;
	@ManyToOne
	@JoinColumn(name = "product_item_id")
	private ProductItems productItems;
	@ManyToOne
	@JoinColumn(name = "promotion_id")
	private Promotions promotions;

}
