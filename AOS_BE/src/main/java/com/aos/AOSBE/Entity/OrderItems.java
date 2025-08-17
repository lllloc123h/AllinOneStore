package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.*;
import java.util.UUID;

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
	
    @Column(name = "price_at_buy")
    private double priceAtBuy;

    @Column(name = "cost_at_buy")
    private double costAtBuy;
	
    @Column(name = "is_gift")
    private Boolean isGift;
	
    @Column(name = "selling_price")
    private double sellingPrice;
	
    @Column(name = "total", insertable = false, updatable = false)
    private double total;

	@CreationTimestamp
    @Column(name = "created_at",updatable = false)
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
    
    @Column(name = "combo_group")
    private String comboGroup;

    @Column(name = "combo_group_id")
    private UUID comboGroupId;
    
    @Column(name = "combo_qty")
    private Integer comboQty;

}