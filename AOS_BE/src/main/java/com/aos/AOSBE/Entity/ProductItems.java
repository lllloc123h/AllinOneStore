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
@Table(name = "product_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private Integer id;
	
    @Column(name = "cost")
    private Double cost;
	
    @Column(name = "price")
    private Double price;
	
    @Column(name = "turn_buy")
    private int turnBuy;
	
    @Column(name = "description")
    private String description;
	
    @Column(name = "sku")
    private String sku;
	
    @Column(name = "safety_stock")
    private int safetyStock;
	
    @Column(name = "qty")
    private int qty;
	
    @Column(name = "sell_start")
    private LocalDateTime sellStart;
	
    @Column(name = "sell_end")
    private LocalDateTime sellEnd;
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	@ManyToOne
	@JoinColumn(name = "base_id")
	private BaseProducts baseProducts;
	@Column(name = "is_active")
	private boolean isActive;
}
