package com.aos.AOSBE.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "cart_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "qty")
	private int qty;
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Accounts accounts;
	@ManyToOne
	@JoinColumn(name = "product_item_id")
	private ProductItems productItems;
	@ManyToOne
	@JoinColumn(name = "combo_id")
	private Promotions promotions;
	@JoinColumn(name= "combo_group")
	private String comboGroup;
	@JoinColumn(name="combo_qty")
	private Integer comboQty;
	@JoinColumn(name="combo_group_id")
	private UUID comboGroupId;
	@JoinColumn(name="is_gift")
	private Boolean isGift;
}
